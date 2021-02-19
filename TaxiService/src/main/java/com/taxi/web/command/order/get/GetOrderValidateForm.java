package com.taxi.web.command.order.get;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class GetOrderValidateForm extends Command {

	private static final long serialVersionUID = -6774467015259325074L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return Path.GET_ORDER_VALIDATE_FORM_JSP;
	}

}
