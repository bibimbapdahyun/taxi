package com.taxi.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCarForm extends Command {

	private static final long serialVersionUID = 6238689733709677820L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return Path.CAR_FORM_JSP;
	}

}
