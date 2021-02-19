package com.taxi.web.command.registration;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

public class RegisterUser extends Command {

	private static final long serialVersionUID = 230935171363449573L;

	private static AccountDao adao = DAOFactory.getInstance().getAccountDao();

	private static final Logger log = LogManager.getLogger(RegisterUser.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		clearSession(request);
		String forward = Path.GET_ERROR_PAGE;
		try {
			Account account = mapAccount(request);
			log.debug("account: {}", account);
			if (adao.getIdByNumber(account.getLogin()) < 1) {
				adao.addUser(account);
				request.getSession().setAttribute("registerMessage",
						"Your accout was succsesfully created, please login.");
				forward = Path.GET_LOGIN_FORM_CMD;
			} else {
				request.getSession().setAttribute("registerMessage",
						"User with this login already exist." + "Try another number, or connect with manager");
				forward = Path.GET_REGISTER_FORM_JSP_CMD;
			}
		} catch (SQLException e) {
			log.error("Somthing was wrong");
			request.setAttribute("errorMessage", "Can not create user!!!");
			e.printStackTrace();
		}
		return forward;
	}

	private void clearSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("registerMessage") != null) {
			session.removeAttribute("registerMessage");
		}
		if(session.getAttribute("errorMessage") != null) {
			session.removeAttribute("errorMessage");
		}
		
	}

	private Account mapAccount(HttpServletRequest request) {
		Hash hash = new ShaHash();
		Account account = new Account();
		account.setLogin(request.getParameter("login"));
		try {
			account.setPassword(hash.hash(request.getParameter("password")));
		} catch (NoSuchAlgorithmException e) {
			log.error("Algorithm not found");
			e.printStackTrace();
		}
		account.setName(request.getParameter("name"));
		account.setGender(getGender(request));
		account.setRole(getRole(request));
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
