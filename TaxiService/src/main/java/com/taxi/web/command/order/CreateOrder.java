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
import com.taxi.entity.CarType;
import com.taxi.entity.CarsCountPrice;
import com.taxi.entity.Order;
import com.taxi.function.DefineDistance;
import com.taxi.function.Distance;
import com.taxi.function.Time;
import com.taxi.function.WaitingTime;
import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class CreateOrder extends Command {

	private static final long serialVersionUID = 1359807440088307352L;

	private static transient Time wTime = new WaitingTime();
	private static transient Distance distance = new DefineDistance();

	private static Logger log = LogManager.getLogger(CreateOrder.class);

	/**
	 * Add order to database, and create receipt.
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
				CarsCountPrice count = mapCount(ccp, request);
				cars = count.getCars();
				order.setType(count.getType());
				order.setPrice(count.getPrice());
				log.debug("price: {}", order.getPrice());
			} else {
				order.setType(((Car) session.getAttribute("Car")).getCarType());
				cars.add((Car) session.getAttribute("Car"));
				log.debug("Current Car: {}", cars);
			}
			int time = 0;
			if (cars.size() > 1) {
				List<Integer> times = new ArrayList<>();
				for (Car car : cars) {
					times.add(wTime.time(distance.getDistance(car.getCurrentPosition(), order.getStart())));
				}
				time = getMaxTime(times);
			} else {
				time = wTime.time(distance.getDistance(cars.get(0).getCurrentPosition(), order.getStart()));
			}
			odao.createOrder(order, cars);
			session.setAttribute("cars", cars);
			session.setAttribute("time", time);
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

	private CarsCountPrice mapCount(List<CarsCountPrice> ccp, HttpServletRequest req) {
		CarsCountPrice count = null;
		if (ccp.size() == 1) {
			return ccp.get(0);
		}
		@SuppressWarnings("unchecked")
		List<CarType> types = (List<CarType>) req.getServletContext().getAttribute("carTypes");
		for (CarsCountPrice c : ccp) {
			for (CarType t : types) {
				if (t.equals(c.getType())) {
					count = c;
					break;
				}
			}
			if(count != null) {
				break;
			}
		}
		return count;
	}

	private int getMaxTime(List<Integer> times) {
		int t = 0;
		for (Integer i : times) {
			if (i > t) {
				t = i;
			}
		}
		return t;
	}

}
