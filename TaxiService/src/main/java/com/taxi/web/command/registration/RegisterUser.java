package com.taxi.web.command.registration;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	/**
	 * The method of class add user to db.
	 */
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		clearSession(request);
		HttpSession session = request.getSession();
		if(!validate(request)) {
			session.setAttribute("registerMessage", "Data is not valid. Try again");
			return Path.GET_REGISTER_FORM_JSP_CMD;
		}
		String forward = Path.GET_ERROR_PAGE;
		try {
			Account account = mapAccount(request);
			log.debug("account: {}", account);
			if (adao.getIdByNumber(account.getLogin()) < 1) {
				adao.addUser(account);
				session.setAttribute("registerMessage",
						"Your accout was succsesfully created, please login.");
				forward = Path.GET_LOGIN_FORM_CMD;
			} else {
				session.setAttribute("registerMessage",
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

	private static final String LOGIN_VALID = "([0-9]{12})";
	private static final String NAME_VALID = "([A-ZА-ЯЁ][a-zа-яё]+)";
	
	private boolean validate(HttpServletRequest request) {
		if(!validRegex(request.getParameter("login"), LOGIN_VALID)) {
			return false;
		}
		return validRegex(request.getParameter("name"), NAME_VALID);
	}
	
	private boolean validRegex(String input, String regex) {
		StringBuilder sb = new StringBuilder();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		if(m.find()) {
			sb.append(m.group());
		}
		return input.contentEquals(sb.toString());
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
