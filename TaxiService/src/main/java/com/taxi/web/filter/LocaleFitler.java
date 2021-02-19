package com.taxi.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LocaleFitler
 */
@WebFilter("/controller")
public class LocaleFitler implements Filter {

	private static final String LOCALE = "javax.servlet.jsp.jstl.fmt.locale";

	public void destroy() {
		// nothing
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		if(session.getAttribute(LOCALE) == null) {
			String header = req.getHeader("Accept-Language");
			if(header == null) {
				session.setAttribute(LOCALE, "en");
			}else {
				session.setAttribute(LOCALE, getLocaele(header));
			}
		}
		chain.doFilter(request, response);
	}

	private String getLocaele(String header) {
		return header.substring(0, header.indexOf(","));
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// nothing
	}

}
