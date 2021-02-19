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

import com.taxi.dao.CarDao;
import com.taxi.dao.CarTypeDao;
import com.taxi.dao.DAOFactory;
import com.taxi.dao.mysql.MySqlCarTypeDao;
import com.taxi.entity.Account;
import com.taxi.entity.Car;
import com.taxi.entity.CarType;
import com.taxi.entity.CarsCountPrice;
import com.taxi.entity.Order;
import com.taxi.function.Calculator;
import com.taxi.function.DefineDistance;
import com.taxi.function.Discount;
import com.taxi.function.DiscountImpl;
import com.taxi.function.Distance;
import com.taxi.function.PriceCalculator;
import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class SetOrder extends Command {
	private static final long serialVersionUID = 1L;

	private final transient Discount discount = new DiscountImpl();
	private final transient Calculator calc = new PriceCalculator();
	private final transient Distance distance = new DefineDistance();
	private final transient CarDao cdao = DAOFactory.getInstance().getCarDao();
	private final transient CarTypeDao tdao = DAOFactory.getInstance().getTypeDao();
	

	private static final Logger log = LogManager.getLogger(SetOrder.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Order order = new Order();
		String forward = Path.GET_ERROR_PAGE;
		HttpSession session = request.getSession();
		clearSession(session);
		try {
			order.setStart(request.getParameter("from"));
			order.setFinish(request.getParameter("to"));
			order.setAccount((Account)session.getAttribute("account"));
			if(order.getAccount().getLogin() == null) {
				clearSession(session);
				session.setAttribute("orderMessage", "You need to login befor create order");
				return Path.GET_LOGIN_FORM_CMD;
			}
			order.setPlaces(Integer.parseInt(request.getParameter("places")));
			order.setType(tdao.getTypeParameter(request.getParameter("type")));
			log.trace("Places = {}", order.getPlaces());
//			if (order.getPlaces() > 10) {
//				log.trace("Now we in places > 10");
//				session.setAttribute("placesMessage",
//						"To place your order, you must contact the operator. If you want to place an order, click confirm and our operator will contact you shortly.");
//				forward = Path.VALIDATE_ORDER_CMD;
//				// TODO command realization
//			} else {
				log.debug("Now we in else block");

				Car car = cdao.getCarByTypePlaces(tdao.getTypeParameter(order.getType().getType()), order.getPlaces());
				if (!car.equals(new Car())) {
					log.debug("car not null");
					int price = calc.calculate(distance.getDistance(order.getStart(), order.getFinish()),
							tdao.getTypeParameter(order.getType().getType()));
					order.setPrice(discount.discount(price));
					session.setAttribute("Car", car);
				} else {
					log.debug("car is null");
					List<CarsCountPrice> ccp = mapCarsByType(order);
					if (ccp.isEmpty()) {
						log.debug("cars not found");
						session.setAttribute("carsNotFound", "Cars not found");
					} else {
						session.setAttribute("message",
								"We can't find one car for your order, but you can choose cars from option below");
					}
					session.setAttribute("ccp", ccp);
					log.debug("ccp was set");
				}
				forward = Path.VALIDATE_ORDER_CMD;
//			}
			session.setAttribute("order", order);
		} catch (SQLException e) {
			session.setAttribute("errorMessage",
					"Failed to create your order. Please try again later. We are sorry for the inconvenience.");
			e.printStackTrace();
		}
		log.trace("order was set");
		log.trace("================================================");
		log.debug("session ccp: {}", session.getAttribute("ccp"));
		log.debug("session car: {}", session.getAttribute("Car"));
		log.debug("placesMessage: {}", session.getAttribute("placesMessage"));
		log.debug("order: {}", session.getAttribute("order"));
		return forward;
	}

	private List<CarsCountPrice> mapCarsByType(Order order) throws SQLException {
		log.debug("mapCars statr work");
		List<CarsCountPrice> ccp = new ArrayList<>();
		List<CarType> types = tdao.getAllTypes();
		int dist = distance.getDistance(order.getStart(), order.getFinish());
		for (CarType carType : types) {
			List<Car> cars = cdao.chooseCars(cdao.getFreeCarsByPlaces(carType.getType()), order.getPlaces());
			CarsCountPrice currentCarsbyType = new CarsCountPrice();
			int price = cars.size()
					* calc.calculate(dist, MySqlCarTypeDao.getInstance().getTypeParameter(carType.getType()));
			price = discount.discount(price);
			log.debug("Price in mapCar: {}", price);
			currentCarsbyType.setCars(cars);
			currentCarsbyType.setPrice(price);
			currentCarsbyType.setType(carType);
			if (price > 0) {
				ccp.add(currentCarsbyType);
				log.debug("Cars was added in cct: {}", currentCarsbyType);
			}
		}
		log.debug("mapCars end work");
		return ccp;
	}

	private void clearSession(HttpSession session) {
		session.removeAttribute("ccp");
		session.removeAttribute("Car");
		session.removeAttribute("message");
		session.removeAttribute("placesMessage");
		session.removeAttribute("errorMessage");
		session.removeAttribute("carsNotFound");
	}

}
