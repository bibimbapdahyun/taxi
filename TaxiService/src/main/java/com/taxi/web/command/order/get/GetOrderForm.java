package com.taxi.web.command.order.get;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class GetOrderForm extends Command{

	private static final long serialVersionUID = 8742068741877897597L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return Path.GET_ORDER_FORM_JSP;
	}

}
