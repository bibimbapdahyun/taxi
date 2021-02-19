package com.taxi.web.command.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class Logout extends Command{

	private static final long serialVersionUID = -5296430643134839464L;

	private static final Logger log = LogManager.getLogger(Logout.class);  
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("logined user befor: {}", request.getSession().getAttribute("user"));
		request.getSession().invalidate();
		log.debug("logined user after: {}", request.getSession().getAttribute("user"));
		return Path.GET_INDEX_JSP_CMD;
	}
}
