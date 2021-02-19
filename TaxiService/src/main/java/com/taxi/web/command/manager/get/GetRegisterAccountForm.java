package com.taxi.web.command.manager.get;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class GetRegisterAccountForm extends Command {

	private static final long serialVersionUID = -4038211046239326341L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.getSession().setAttribute("role", request.getParameter("role"));
		return Path.GET_REGISTER_ACCOUNT_FORM;
	}
}