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

public class SortByPrice extends Command {

	private static final String ORDERS = "orders";
	private static OrderDao odao = DAOFactory.getInstance().getOrderDao();
	private static CarDao cdao = DAOFactory.getInstance().getCarDao();

	private static final int LIMIT = 5;
	private static final Logger log = LogManager.getLogger(SortByPrice.class);

	private static final long serialVersionUID = 6384423604290636287L;

	/**
	 * The method gets and set order in session by limit and sorted by price.
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
			String sort = getSort(request, session);
			int totalSize = (odao.getCountOfOrders() / LIMIT) + 1;
			orders = getOrdersBySort(request, offset, sort);
			session.setAttribute("pageCount", calculatePageCount(totalSize));
			session.setAttribute(ORDERS, orders);
			forward = Path.GET_STATISTICS_PAGE;
		} catch (SQLException e) {
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

	private List<Order> getOrdersBySort(HttpServletRequest request, int offset, String sort) throws SQLException {
		List<Order> orders;
		boolean flag;
		if ("up".equals(sort)) {
			flag = false;
		} else {
			flag = true;
		}
		orders = odao.getOrderByPrice(flag, LIMIT, currentPosition(LIMIT, offset));
		orders = mapOrders(orders, request);
		return orders;
	}

	private List<Order> mapOrders(List<Order> orders, HttpServletRequest request) throws SQLException {
		log.debug("Orders: {}", orders);
		if (orders.isEmpty()) {
			request.getSession().setAttribute("errorMessage", "Orders not found");
		} else {
			for (Order order : orders) {
				order.setCar(cdao.getCarsInOrder(order.getId()));
			}
			request.getSession().setAttribute(ORDERS, orders);
			log.debug("Orders session: {}", request.getSession().getAttribute(ORDERS));
		}
		return orders;
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
	
	private int currentPosition(int limit, int offset) {
		return (offset - 1) * limit;
	}

}
