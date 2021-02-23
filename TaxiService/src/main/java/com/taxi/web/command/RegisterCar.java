package com.taxi.web.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taxi.dao.AccountDao;
import com.taxi.dao.DAOFactory;
import com.taxi.entity.Account;
import com.taxi.entity.Car;
import com.taxi.entity.CarType;

public class RegisterCar extends Command {

	private static final long serialVersionUID = 3306678792572430399L;

	private static AccountDao adao = DAOFactory.getInstance().getAccountDao();
	
	/**
	 * The class add driver and car to database.
	 */
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String forward = Path.GET_ERROR_PAGE;
		Car car = mapCar(request);
		Account account = (Account) request.getSession().getAttribute("regAccount");
		try {
			adao.createDriver(car, account);
			forward = Path.GET_INDEX_JSP_CMD;
		} catch (SQLException e) {
			request.getSession().setAttribute("errorMessage", "Can't add driver. Try later.");
		}
		return forward;
	}

	private Car mapCar(HttpServletRequest request) {
		Car car = new Car();
		car.setCarNumber(request.getParameter("number"));
		car.setMark(request.getParameter("mark"));
		car.setPlaces(Integer.parseInt(request.getParameter("place")));
		car.setCarType(getCarType(request));
		return car;
	}

	private CarType getCarType(HttpServletRequest request) {
		CarType ct = new CarType();
		@SuppressWarnings("unchecked")
		List<CarType> types = (List<CarType>) request.getServletContext().getAttribute("carTypes");
		String type = request.getParameter("type");
		for(CarType t : types) {
			if(t.getType().equals(type)) {
				ct = t;
				break;
			}
		}
		return ct;
	}

}
