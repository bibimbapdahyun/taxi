package com.taxi.web.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		HttpSession session = request.getSession();
		clearSession(session);
		if(!validate(request)) {
			session.setAttribute("message", "Input data is inavalid.");
			return Path.GET_CAR_FORM_CMD;
		}
		String forward = Path.GET_ERROR_PAGE;
		Car car = mapCar(request);
		Account account = (Account) session.getAttribute("regAccount");
		try {
			adao.createDriver(car, account);
			forward = Path.GET_INDEX_JSP_CMD;
		} catch (SQLException e) {
			session.setAttribute("errorMessage", "Can't add driver. Try later.");
		}
		return forward;
	}

	private void clearSession(HttpSession session) {
		session.removeAttribute("message");
		
	}

	private static final String CAR_NUMBER_REGEX = "([A-Z]{2})([0-9]{4})([A-Z]{2})";
	private static final String CAR_MARK_REGEX = "([A-Z][a-z]+\\s)([A-Z][a-z]+)";
	
	private boolean validate(HttpServletRequest request) {
		if (!regex(request.getParameter("number"), CAR_NUMBER_REGEX)) {
			return false;
		}
		return !regex(request.getParameter("Mark"), CAR_MARK_REGEX);
	}

	private boolean regex(String input, String regex) {
		if(input == null) {
			return false;
		}
		String str = input.trim();
		StringBuilder sb = new StringBuilder();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		while (m.find()) {
			sb.append(m.group());
		}
		return str.contentEquals(sb.toString());
	}

	private Car mapCar(HttpServletRequest request) {
		Car car = new Car();
		car.setCarNumber(request.getParameter("number").trim());
		car.setMark(request.getParameter("mark").trim());
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
