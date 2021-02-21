package com.taxi.web.command;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.taxi.dao.CarDao;
import com.taxi.dao.DAOFactory;
import com.taxi.entity.Account;

public class GetAccountState extends Command {

	private static final long serialVersionUID = 3454397436847787427L;

	private static transient CarDao cdao = DAOFactory.getInstance().getCarDao();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String forward = Path.GET_ERROR_PAGE;
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("account");
		try {
			session.setAttribute("car", cdao.getCarByAccountId(account));
			forward = Path.GET_CHANGE_STATE_CMD;
		} catch (SQLException e) {
			session.setAttribute("errorMessage", "Account not found");
		}
		return forward;
	}

}
