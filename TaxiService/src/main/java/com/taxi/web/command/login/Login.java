package com.taxi.web.command.login;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.dao.AccountDao;
import com.taxi.dao.DAOFactory;
import com.taxi.entity.Account;
import com.taxi.function.Hash;
import com.taxi.function.ShaHash;
import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class Login extends Command {

	private static final String ACCOUNT = "account";

	private static final long serialVersionUID = -7081511361672401462L;

	private static final Logger log = LogManager.getLogger(Login.class);

	private static AccountDao adao = DAOFactory.getInstance().getAccountDao();

	/**
	 * The method check user and password. If user is in the database and password
	 * equals to password from database set account in session.
	 */

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.trace("Login command execute start working");
		String forward = Path.GET_ERROR_PAGE;
		try {
			Hash hash = new ShaHash();
			String login = request.getParameter("login").trim();
			log.debug("Login: {}", login);
			String password = request.getParameter("password");
			log.debug("Password: {}", password);
			Account account = adao.getAccountByLogin(login);
			log.debug("Account: {}", account);
			// ==============================//
//			if ("380666658673".equals(account.getPassword())) {
//				request.getSession().setAttribute(ACCOUNT, account);
//				return Path.GET_INDEX_JSP_CMD;
//			}
			// ==============================//
			String hashPasswd = hash.hash(password);
			log.debug("hashed password: {}", hashPasswd);
			if (hashPasswd.equals(account.getPassword())) {
				request.getSession().setAttribute(ACCOUNT, account);
				forward = Path.GET_INDEX_JSP_CMD;
			} else {
				request.getSession().setAttribute("loginMessage", "Login or password incorrect.");
				forward = Path.GET_LOGIN_FORM_CMD;
			}
			log.debug("Session account: {}", request.getSession().getAttribute(ACCOUNT));
		} catch (SQLException e) {
			log.error("Troble in hash function");
			request.getSession().setAttribute("errorMessage", "Sorry but something not working, try later");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		log.trace("Login command execute end working");
		log.trace("Forward to --> {}", forward);
		return forward;
	}
}
