package com.taxi.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetDriverConfirmOrder extends Command {

	private static final long serialVersionUID = -6908229890943450494L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return Path.DRIVER_CONFIRM_ORDER;
	}

}
