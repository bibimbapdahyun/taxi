package com.taxi.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BackToIndex extends Command {

	private static final long serialVersionUID = -1578009325134575698L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return Path.GET_INDEX_JSP_CMD;
	}

}
