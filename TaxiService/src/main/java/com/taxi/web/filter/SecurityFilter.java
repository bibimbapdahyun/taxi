package com.taxi.web.filter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.entity.Account;
import com.taxi.entity.Role;
import com.taxi.web.command.Path;

//@WebFilter
public class SecurityFilter implements Filter {
	private static final Logger log = LogManager.getLogger(SecurityFilter.class);

	Map<Role, List<String>> access = new HashMap<>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try (FileReader fr = new FileReader(new File(filterConfig.getServletContext().getRealPath(
				"WEB-INF/usercommand.properties")));) {
			Properties props = new Properties();
			props.load(fr);
			Set<String> propsName = props.stringPropertyNames();
			log.debug("props: {}", propsName);
			@SuppressWarnings("unchecked")
			List<Role> roles = (List<Role>) filterConfig.getServletContext().getAttribute("accountRoles");
			for (String name : propsName) {
				if ("*".equals(name)) {
					access.put(null, mapList(props.getProperty(name)));
				} else {
					access.put(getRole(roles, name), mapList(props.getProperty(name)));
				}
				log.debug("Role: {}", name);
			}
		} catch (IOException e) {
			log.fatal("Can not read property file.");
			throw new IllegalStateException("Can not init Securityfilter", e);
		} catch (NoSuchElementException ex) {
			log.fatal("Cant find role.");
			throw new IllegalStateException("Can not find the account role", ex);
		}
		log.debug("access: {}", access);
	}

	private Role getRole(List<Role> roles, String name) {
		for (Role r : roles) {
			if (r.getRole().equals(name)) {
				return r;
			}
		}
		throw new NoSuchElementException();
	}

	private List<String> mapList(String param) {
		List<String> list = new ArrayList<>();
		String[] commands = param.split("\s");
		Collections.addAll(list, commands);
		return list;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (accessAllowed(request)) {
			log.debug("Access permission");
			chain.doFilter(request, response);
		} else {
			log.debug("You have not permission");
			HttpServletRequest req = (HttpServletRequest) request;
			req.getSession().setAttribute("errorMessage", "You have no permission to do this operation");
			request.getRequestDispatcher(Path.GET_ERROR_PAGE).forward(request, response);
		}
		log.debug("filter#doFilter");
	}

	private boolean accessAllowed(ServletRequest request) {
		HttpServletRequest req = (HttpServletRequest) request;
		String command = req.getParameter("command");
		log.debug("access allowed: command --> {}", command);
		if (command == null || command.isEmpty()) {
			return false;
		}
		if (access.get(null).contains(command)) {
			return true;
		}
		HttpSession session = req.getSession();
		if (session == null) {
			return false;
		}
		Account account = (Account) session.getAttribute("account");
		log.debug("access allowed: account --> {}", account);
		if(account == null) {
			return access.get(null).contains(command);
		}
		return access.get(account.getRole()).contains(command);
	}

	@Override
	public void destroy() {
		// nothing
	}
}
