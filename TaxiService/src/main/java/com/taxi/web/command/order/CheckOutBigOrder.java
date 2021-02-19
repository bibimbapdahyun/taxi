package com.taxi.web.command.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class CheckOutBigOrder extends Command {

	private static final long serialVersionUID = 7458597360861501514L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO add request for administrator to make order and call to client
		request.getSession().setAttribute("confrimBigOrder", "Thank you for order!!!");
		// was redirect 
		return Path.SHOW_MESSAGE_FOR_USER;
	}

}
