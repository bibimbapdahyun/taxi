package com.taxi.web.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.dao.CarDao;
import com.taxi.dao.CarTypeDao;
import com.taxi.dao.DAOFactory;
import com.taxi.entity.Account;
import com.taxi.entity.Car;
import com.taxi.entity.CarState;
import com.taxi.entity.CarType;

public class ChangeAccountState extends Command {

	private static final String WAITING = "waiting";
	private static final String INACTIVE = "inactive";

	private static final long serialVersionUID = -7190734657530804273L;

	private static final Logger log = LogManager.getLogger(ChangeAccountState.class);
	
	private static transient CarDao cdao = DAOFactory.getInstance().getCarDao();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String forward = Path.GET_ERROR_PAGE;
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("account");
		try {
			Car car = cdao.getCarByAccountId(account);
			CarState cs = car.getState();
			log.debug("carType: {}", cs);
			if(WAITING.equals(cs.getName())) {
				log.debug(WAITING);
				cdao.changeStateId(getCarState(INACTIVE, request), car);
				session.setAttribute("car", car);
				log.debug("car: {}", car);
			}else {
				log.debug(INACTIVE);
				cdao.changeStateId(getCarState(WAITING, request), car);
				session.setAttribute("car", car);
				log.debug("car: {}", car);
			}
			forward = Path.CHANGE_ACCOUNT_STATE_CMD;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return forward;
	}

	private CarState getCarState(String str, HttpServletRequest request) {
		ServletContext sc = request.getServletContext();
		CarState c = new CarState();
		c.setName(str);
		List<CarState> states = (List<CarState>) sc.getAttribute("carStates");
		for(CarState cs : states) {
			if(cs.getName().equals(c.getName())) {
				c = cs;
				break;
			}
		}
		return c;
	}

}
