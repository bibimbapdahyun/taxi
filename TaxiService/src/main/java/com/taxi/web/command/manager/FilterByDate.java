package com.taxi.web.command.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.dao.CarDao;
import com.taxi.dao.DAOFactory;
import com.taxi.dao.OrderDao;
import com.taxi.entity.Order;
import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class FilterByDate extends Command {

	private static final String START_DATE = "startDate";

	private static final String FINISH_DATE = "finishDate";

	private static final long serialVersionUID = -3970791079240833025L;

	private static OrderDao odao = DAOFactory.getInstance().getOrderDao();
	private static CarDao cdao = DAOFactory.getInstance().getCarDao();

	private static final Logger log = LogManager.getLogger(FilterByDate.class);

	private static final int LIMIT = 5;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String forward = Path.GET_ERROR_PAGE;
		HttpSession session = request.getSession();
		if (request.getParameter(START_DATE) != session.getAttribute(START_DATE)
				&& request.getParameter(START_DATE) != null) {
			clearSession(session);
		}
		try {
			int offset = Integer.parseInt(request.getParameter("page"));
			request.setAttribute("cmd", request.getParameter("command"));
			request.setAttribute("page", request.getParameter("page"));
			session.setAttribute("currentPosition", currentPosition(LIMIT, offset));
			String d = request.getParameter("date");
			log.debug("Date: {}", d);
			Date startDate;
			if (request.getParameter(START_DATE) == null || request.getParameter(START_DATE).isEmpty()) {
				startDate = (Date) session.getAttribute(START_DATE);
			} else {
				startDate = setStartDate(request, session);
			}
			Date finishDate;
			if (((Date) session.getAttribute(FINISH_DATE)) == null) {
				finishDate = new Date();
				int totalSize = odao.getCountOfOrdersByDate(startDate, finishDate);
				session.setAttribute("pageCount", calculatePageCount(totalSize));
				session.setAttribute(FINISH_DATE, finishDate);
			} else {
				finishDate = (Date) session.getAttribute(FINISH_DATE);
			}
			List<Order> orders = odao.getOrdersByDate(startDate, finishDate, LIMIT, currentPosition(LIMIT, offset));

			log.debug("Orders: {}", orders);
			if (orders.isEmpty()) {
				request.getSession().setAttribute("errorMessage", "Orders not found");
				forward = Path.GET_STATISTICS_PAGE;
			} else {
				for (Order order : orders) {
					order.setCar(cdao.getCarsInOrder(order.getId()));
				}
				request.getSession().setAttribute("orders", orders);
				log.debug("Orders session: {}", request.getSession().getAttribute("orders"));
				forward = Path.GET_STATISTICS_PAGE;
			}
		} catch (SQLException e) {
			session.setAttribute("errorMessage", "ERROR!");
			e.printStackTrace();
		}
		return forward;
	}

	private int calculatePageCount(int totalSize) {
		if(totalSize % LIMIT == 0) {
			return totalSize / LIMIT;
		}
		return totalSize / LIMIT + 1;
	}
	
	private Date setStartDate(HttpServletRequest request, HttpSession session) {
		Date startDate;
		try {
			startDate = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH).parse(request.getParameter(START_DATE));
			session.setAttribute(START_DATE, startDate);
		} catch (ParseException e) {
			startDate = new Date();
		}
		return startDate;
	}

	private void clearSession(HttpSession session) {
		session.removeAttribute(START_DATE);
		session.removeAttribute(FINISH_DATE);
		session.removeAttribute("pageCount");
		session.removeAttribute("currentPostition");
	}

	private int currentPosition(int limit, int offset) {
		return (offset - 1) * limit;
	}

}
