package com.taxi.web.command.registration.get;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class GetRegisterForm extends Command{

	private static final long serialVersionUID = -1477219619587448710L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return Path.GET_REGISTER_FORM_JSP;
	}
	

}
