package com.taxi.web.command.manager;

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

public class CreateAccount extends Command {

	private static AccountDao adao = DAOFactory.getInstance().getAccountDao();

	private static final Logger log = LogManager.getLogger(CreateAccount.class);
	private static final long serialVersionUID = -9161099608307654194L;

	/**
	 * The method add user to database by data input the administrator. If
	 * registered user is driver also add car for this driver.
	 */

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		clearSession(request);
		String forward = Path.GET_ERROR_PAGE;
		try {
			Role role = getRole(request);
			if ("driver".equals(role.getRole())) {
				request.getSession().setAttribute("regAccount", mapAccount(request));
				forward = Path.GET_CAR_FORM_CMD;
			} else {
				String login = request.getParameter("login");
				if (adao.getIdByNumber(login) < 1) {
					adao.addUser(mapAccount(request));
					forward = Path.GET_INDEX_JSP_CMD;
				} else {
					request.getSession().setAttribute("errorMessage", "Account with this login already exists");
					forward = Path.GET_REGISTER_ACCOUNT_FORM;
				}
			}
		} catch (SQLException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return forward;
	}

	private void clearSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("errorMessage") != null) {
			session.removeAttribute("errorMessage");
		}
	}

	private Account mapAccount(HttpServletRequest request) throws NoSuchAlgorithmException {
		Account account = new Account();
		Hash hash = new ShaHash();
		account.setLogin(request.getParameter("login"));
		account.setPassword(hash.hash(request.getParameter("password")));
		account.setName(request.getParameter("name"));
		account.setSurname(request.getParameter("surname"));
		account.setMail(request.getParameter("mail"));
		account.setRole(getRole(request));
		account.setGender(getGender(request));
		return account;
	}

	private Gender getGender(HttpServletRequest request) {
		Gender result = new Gender();
		result.setName(request.getParameter("g"));
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

	private Role getRole(HttpServletRequest request) {
		Role role = new Role();
		@SuppressWarnings("unchecked")
		List<Role> roles = (List<Role>) request.getServletContext().getAttribute("accountRoles");
		log.debug("Roles: {}", roles);
		for (Role r : roles) {
			log.debug("Role: --> {}", role);
			if (r.getRole().equals(request.getSession().getAttribute("role"))) {
				role = r;
				break;
			}
		}
		log.debug("Role: --> {}", role);
		return role;
	}

}
