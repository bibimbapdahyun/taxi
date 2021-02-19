package com.taxi.web.command.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class CancelOrder extends Command {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		if (session.getAttribute("order") != null) {
			session.removeAttribute("order");
		}
		if (session.getAttribute("cars") != null) {
			session.removeAttribute("cars");
		}
		if (session.getAttribute("ccp") != null) {
			session.removeAttribute("ccp");
		}
		if (session.getAttribute("car") != null) {
			session.removeAttribute("car");
		}
		if (session.getAttribute("errorMessage") != null) {
			session.removeAttribute("errorMessage");
		}
		// TODO maybe something need to clear
		// was redirect
		return Path.GET_INDEX_JSP_CMD;
	}

}
