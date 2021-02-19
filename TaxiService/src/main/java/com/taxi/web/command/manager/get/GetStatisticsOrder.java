package com.taxi.web.command.manager.get;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class GetStatisticsOrder extends Command {

	private static final long serialVersionUID = 1421346544391077589L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return Path.GET_STATISTICS_ORDER_JSP;
	}

}
