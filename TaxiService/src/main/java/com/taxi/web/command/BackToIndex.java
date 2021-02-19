package com.taxi.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BackToIndex extends Command {

	private static final long serialVersionUID = -1578009325134575698L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		if (session.getAttribute("ccp") != null) {
			session.removeAttribute("ccp");
		}
		if (session.getAttribute("ccp") != null) {
			session.removeAttribute("Car");
		}
		if (session.getAttribute("ccp") != null) {
			session.removeAttribute("order");
		}
		if (session.getAttribute("ccp") != null) {
			session.removeAttribute("role");
		}
		return Path.GET_INDEX_JSP_CMD;
	}

}
