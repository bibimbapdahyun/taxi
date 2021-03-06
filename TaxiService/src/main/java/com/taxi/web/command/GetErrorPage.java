package com.taxi.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetErrorPage extends Command {

	private static final long serialVersionUID = 2256353886436066363L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return Path.SHOW_ERROR_PAGE;
	}

}
