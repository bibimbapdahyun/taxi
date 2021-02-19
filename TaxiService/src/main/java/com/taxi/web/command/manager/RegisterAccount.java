package com.taxi.web.command.manager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.dao.AccountDao;
import com.taxi.dao.DAOFactory;
import com.taxi.entity.Account;
import com.taxi.entity.Gender;
import com.taxi.entity.Role;
import com.taxi.function.Hash;
import com.taxi.function.ShaHash;
import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class RegisterAccount extends Command {

	private static final long serialVersionUID = -4603105503683557328L;

	private static final Logger log = LogManager.getLogger(RegisterAccount.class);

	private static AccountDao adao = DAOFactory.getInstance().getAccountDao();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String forward = Path.GET_ERROR_PAGE;
		Account account = mapAccount(request);
		try {
			if (adao.getIdByNumber(account.getLogin()) < 1) {
				adao.addUser(account);
				request.getSession().setAttribute("registerMessage",
						"Accout was succsesfully registred, please login.");
				forward = Path.GET_INDEX_JSP_CMD;
			} else {
				request.getSession().setAttribute("registerMessage",
						"User with this login was created. " + "Try another number, or connect with manager");
				forward = Path.REGISTER_ACCOUNT_FORM_JSP;
			}
		} catch (SQLException e) {
			log.error("Somthing was wrong");
			request.setAttribute("errorMessage", "Can not create user!!!");
			e.printStackTrace();
		}
		return forward;
	}

	private Account mapAccount(HttpServletRequest request) {
		Account account = new Account();
		Hash hash = new ShaHash();
		try {
			account.setLogin(request.getParameter("login"));
			account.setPassword(hash.hash(request.getParameter("password")));
			account.setName(request.getParameter("name"));
			account.setGender(getGender(request));
			account.setRole(getRole(request));
			account.setMail(request.getParameter("mail"));
		} catch (NoSuchAlgorithmException e) {
			request.getSession().setAttribute("errorMessage", "Sorry something was wrong. Try later.");
			e.printStackTrace();
		}
		return account;
	}

	private Role getRole(HttpServletRequest request) {
		Role role = new Role();
		@SuppressWarnings("unchecked")
		List<Role> roles = (List<Role>) request.getServletContext().getAttribute("accountRoles");
		for (Role r : roles) {
			if (r.getRole().equals("user")) {
				role = r;
				break;
			}
		}
		return role;
	}

	private Gender getGender(HttpServletRequest request) {
		Gender result = new Gender();
		result.setName(request.getParameter("gender"));
		@SuppressWarnings("unchecked")
		List<Gender> genders = (List<Gender>) request.getServletContext().getAttribute("genders");
		for (Gender gender : genders) {
			if (result.getName().equals(gender.getName())) {
				result = gender;
				break;
			}
		}
		return result;
	}

}
