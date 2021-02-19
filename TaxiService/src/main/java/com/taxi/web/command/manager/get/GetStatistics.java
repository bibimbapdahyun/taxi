package com.taxi.web.command.manager.get;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

public class GetStatistics extends Command {

	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String ORDER = "orders";

	private static OrderDao odao = DAOFactory.getInstance().getOrderDao();
	private static CarDao cdao = DAOFactory.getInstance().getCarDao();

	private static final Logger log = LogManager.getLogger(GetStatistics.class);

	private static final long serialVersionUID = -8264471662120735346L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		clearSession(session);
		String forward = Path.GET_ERROR_PAGE;
		int limit = 5;
		int offset = Integer.parseInt(request.getParameter("page"));
		request.setAttribute("cmd", request.getParameter("command"));
		request.setAttribute("page", request.getParameter("page"));
		log.debug("Page: {}", offset);
		List<Order> orders = new ArrayList<>();
		try {
			int totalSize = (odao.getCountOfOrders() / limit) + 1;
			session.setAttribute("pageCount", totalSize);
			session.setAttribute("currentPosition", currentPosition(limit, offset));
			orders = odao.getOrderCount(limit, currentPosition(limit, offset));
			log.debug("Orders: {}", orders);
			if (orders.isEmpty()) {
				session.setAttribute(ERROR_MESSAGE, "Orders not found");
				forward = Path.GET_STATISTICS_PAGE;
			} else {
				for (Order order : orders) {
					order.setCar(cdao.getCarsInOrder(order.getId()));
				}
				session.setAttribute(ORDER, orders);
				log.debug("Orders session: {}", session.getAttribute(ORDER));
//				forward = Path.GET_STATISTICS_ORDER_JSP;
				forward = Path.GET_STATISTICS_PAGE;
			}
		} catch (SQLException e) {
			request.getSession().setAttribute(ERROR_MESSAGE, "ERROR");
			request.getSession().setAttribute(ERROR_MESSAGE, "We cant get order statistics please try again.");
			e.printStackTrace();
		}
		return forward;
	}

//	private int currentStartPosition(int limit, int offset) {
//		return offset == 1 ? 1 : currentPosition(limit, offset);
//	}

	private int currentPosition(int limit, int offset) {
		return (offset - 1) * limit;
	}


	private void clearSession(HttpSession session) {
		session.removeAttribute(ERROR_MESSAGE);
		session.removeAttribute(ORDER);
	}
}
