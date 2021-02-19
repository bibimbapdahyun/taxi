package com.taxi.web.command.order;

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

import com.taxi.dao.DAOFactory;
import com.taxi.dao.OrderDao;
import com.taxi.entity.Car;
import com.taxi.entity.CarsCountPrice;
import com.taxi.entity.Order;
import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class CreateOrder extends Command {

	private static final long serialVersionUID = 1359807440088307352L;

	private static Logger log = LogManager.getLogger(CreateOrder.class);

	/**
	 * Get cars, order from session Create Order Set for order some cars
	 * (Transaction) Clear session
	 * 
	 * 
	 */

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String forward = Path.GET_ERROR_PAGE;
		HttpSession session = request.getSession();
		OrderDao odao = DAOFactory.getInstance().getOrderDao();
		Order order = (Order) session.getAttribute("order");
		String type = request.getParameter("count");
		log.debug("type: {}", type);
		@SuppressWarnings("unchecked")
		List<CarsCountPrice> ccp = (List<CarsCountPrice>) session.getAttribute("ccp");
		log.debug("ccp: {}", ccp);
		List<Car> cars = new ArrayList<>();
		try {
			if (ccp != null) {
				if (!ccp.isEmpty()) {
					CarsCountPrice count = new CarsCountPrice();
					for (CarsCountPrice c : ccp) {
						if (c.getType().getType().equals(type)) {
							count = c;
							order.setType(count.getType());
							log.debug("count: {}", count);
							break;
						}
					}
					cars = count.getCars();
					order.setPrice(count.getPrice());
					log.debug("price: {}", order.getPrice());
				}
			} else {
				order.setType(((Car) session.getAttribute("Car")).getCarType());
				cars.add((Car) session.getAttribute("Car"));
				log.debug("Current Car: {}", cars);
			}
			odao.createOrder(order, cars);
			session.setAttribute("cars", cars);
			session.setAttribute("order", order);
			forward = Path.GET_ORDER_RECEIPT_CMD;
			log.debug("session cars: {}", cars);
			log.debug("session order: {}", order);
		} catch (SQLException e) {
			session.setAttribute("errorMessage",
					"Failed to create your order. Please try again later. We are sorry for the inconvenience.");
			log.error(e.getMessage());

		}
		// was redirect
		return forward;
	}

}