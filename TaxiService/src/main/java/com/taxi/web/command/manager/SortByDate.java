package com.taxi.web.command.manager;

import java.io.IOException;
import java.sql.SQLException;
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

public class SortByDate extends Command {

	private static final String ORDERS = "orders";

	private static final long serialVersionUID = -1513688416228774314L;

	private static OrderDao odao = DAOFactory.getInstance().getOrderDao();
	private static CarDao cdao = DAOFactory.getInstance().getCarDao();

	private static final int LIMIT = 5;
	private static final Logger log = LogManager.getLogger(SortByDate.class);

	/**
	 * The method get and set order to session by limit sorted by date.
	 */
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		String forward = Path.GET_ERROR_PAGE;
		int offset = Integer.parseInt(request.getParameter("page"));
		request.setAttribute("cmd", request.getParameter("command"));
		request.setAttribute("page", request.getParameter("page"));
		List<Order> orders;
		try {
			int totalSize = odao.getCountOfOrders();
			session.setAttribute("pageCount", calculatePageCount(totalSize));
			session.setAttribute("currentPosition", currentPosition(LIMIT, offset));
			String sort;
			sort = getSort(request, session);
			orders = getOrdersBySort(session, offset, sort);
			session.setAttribute(ORDERS, orders);
			forward = Path.GET_STATISTICS_PAGE;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return forward;
	}

	private List<Order> getOrdersBySort(HttpSession session, int offset, String sort) throws SQLException {
		List<Order> orders;
		if ("up".equals(sort)) {
			orders = odao.getOrderByDate(false, LIMIT, currentPosition(LIMIT, offset));
			orders = mapOrders(orders, session);
			log.debug("orders: {}", orders);
		} else {
			orders = odao.getOrderByDate(true, LIMIT, currentPosition(LIMIT, offset));
			orders = mapOrders(orders, session);
			log.debug("orders: {}", orders);
		}
		return orders;
	}
	
	private int calculatePageCount(int totalSize) {
		if(totalSize % LIMIT == 0) {
			return totalSize / LIMIT;
		}
		return totalSize / LIMIT + 1;
	}

	private String getSort(HttpServletRequest request, HttpSession session) {
		String sort;
		if (request.getParameter("sort") == null) {
			sort = (String) session.getAttribute("sort");
		}else {
			session.setAttribute("sort", request.getParameter("sort"));
			sort = request.getParameter("sort");
		}
		return sort;
	}

	private List<Order> mapOrders(List<Order> orders, HttpSession session) throws SQLException {
		log.debug("Orders: {}", orders);
		if (orders.isEmpty()) {
			session.setAttribute("errorMessage", "Orders not found");
		} else {
			for (Order order : orders) {
				order.setCar(cdao.getCarsInOrder(order.getId()));
			}
			session.setAttribute(ORDERS, orders);
			log.debug("Orders session: {}", session.getAttribute(ORDERS));
		}
		return orders;
	}

	private int currentPosition(int limit, int offset) {
		return (offset - 1) * limit;
	}
}
