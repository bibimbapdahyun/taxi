package com.taxi.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoCommand extends Command {

	private static final long serialVersionUID = 6562601237849742901L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.getSession().setAttribute("errorMessage", "No such command");
		return Path.GET_ERROR_PAGE;
	}

}
