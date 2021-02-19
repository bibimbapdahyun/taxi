package com.taxi.web.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taxi.dao.DAOFactory;
import com.taxi.dao.OrderDao;
import com.taxi.entity.Order;

public class FinishOrder extends Command {

	private static final long serialVersionUID = 1679688213989921407L;

	private static transient OrderDao odao = DAOFactory.getInstance().getOrderDao();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String forward = Path.GET_ERROR_PAGE;
		Order order = (Order)request.getSession().getAttribute("order");
		try {
			odao.finishOrder(order);
			request.getSession().removeAttribute("order");
			request.setAttribute("page", request.getParameter("page"));
			forward = Path.GET_DRIVER_ACTUAL_ORDER;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return forward;
	}

}
