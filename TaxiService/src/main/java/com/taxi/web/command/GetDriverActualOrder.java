package com.taxi.web.command;

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
import com.taxi.entity.Account;
import com.taxi.entity.Car;
import com.taxi.entity.Order;

public class GetDriverActualOrder extends Command {

	private static final long serialVersionUID = 2124027423687442232L;

	private static final int LIMIT = 5;
	private static transient OrderDao odao = DAOFactory.getInstance().getOrderDao();
	private static final Logger log = LogManager.getLogger(GetDriverActualOrder.class);
	private static transient CarDao cdao = DAOFactory.getInstance().getCarDao();

	/**
	 * The method of class return actual order for driver and gives function to
	 * finish this order. Also get all orders for this current driver in session.
	 */

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String forward = Path.GET_ERROR_PAGE;
		HttpSession session = request.getSession();
		try {
			Account account = (Account) session.getAttribute("account");
			Car car = cdao.getCarByAccountId(account);
			// TODO Change odao method
			Order order = odao.getActualOrderCar(car);
			log.debug("order: {}", order);
			if (order.getId() < 1) {
				session.setAttribute("errorMessage", "Actual orders not found");
			} else {
				session.setAttribute("order", order);
			}
			int offset = 1;
			if (request.getParameter("page") != null) {
				offset = Integer.parseInt(request.getParameter("page"));
			}
			request.setAttribute("cmd", request.getParameter("command"));
			request.setAttribute("page", request.getParameter("page"));
			int totalSize = odao.getCountOfOrdersByCar(car);
			session.setAttribute("currentPosition", currentPosition(LIMIT, offset));
			session.setAttribute("pageCount", calculatePageCount(totalSize));
			List<Order> orders = odao.getOrderByDriver(car, LIMIT, currentPosition(LIMIT, offset));
			log.debug("orders: {}", orders);
			session.setAttribute("orders", orders);
			forward = Path.GET_DRIVER_CONFIRM_ORDER;
		} catch (SQLException e) {
			log.error("Somthing was wrong");
			session.setAttribute("errorMessage", "Somthing was wrong");
			e.printStackTrace();
		}
		return forward;
	}

	private int calculatePageCount(int totalSize) {
		if (totalSize % LIMIT == 0) {
			return totalSize / LIMIT;
		}
		return totalSize / LIMIT + 1;
	}

	private int currentPosition(int limit, int offset) {
		return (offset - 1) * limit;
	}
}
