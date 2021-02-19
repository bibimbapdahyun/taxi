package com.taxi.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateLocale extends Command {

	private static final long serialVersionUID = 214644918376796588L;

	private static final Logger log = LogManager.getLogger(UpdateLocale.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String localeToSet = request.getParameter("localeToSet");
		if (localeToSet != null && !localeToSet.isEmpty()) {
			HttpSession session = request.getSession();
			log.debug("locale to update: {}", localeToSet);
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeToSet);			
		}
		return Path.GET_INDEX_JSP_CMD;
	}

}
