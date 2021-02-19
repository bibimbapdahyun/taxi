package com.taxi.web.command.manager.get;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class GetStatisticsPage extends Command {

	private static final long serialVersionUID = -2413961471235450556L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return Path.GET_STATISTICS_ORDER_JSP;
	}

}
