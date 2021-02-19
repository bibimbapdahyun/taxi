package com.taxi.web.listener;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import com.taxi.dao.DAOFactory;

@WebListener
public class ContextListener implements ServletContextListener {

	private static final Logger log = LogManager.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent sce) {
		// nothing
	}

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		initLog4J(sc);
		initI18N(sc);
		try {
			sc.setAttribute("carTypes", DAOFactory.getInstance().getTypeDao().getAllTypes());
			log.trace("car Types {}",sc.getAttribute("carTypes"));
			sc.setAttribute("tripStates",
					DAOFactory.getInstance().getTripStateDao().getAllTripState());
			log.trace("trip States {}",sc.getAttribute("tripStates"));
			sc.setAttribute("carStates",
					DAOFactory.getInstance().getCarStateDao().getAllCarState());
			log.trace("car States {}",sc.getAttribute("carStates"));
			sc.setAttribute("accountRoles",
					DAOFactory.getInstance().getRoleDao().getAllAccountRole());
			log.trace("account Roles {}",sc.getAttribute("accountRoles"));
			sc.setAttribute("genders", DAOFactory.getInstance().getGenderDao().getAllGenders());
			log.trace("genders {}",sc.getAttribute("genders"));
		} catch (SQLException e) {
			log.fatal("Cannot initialize context");
			e.printStackTrace();
			throw new IllegalStateException("Can not init ContextListener", e);
		}
	}
	
	private void initI18N(ServletContext servletContext) {
		log.debug("I18N subsystem initialization started");
		
		String localesValue = servletContext.getInitParameter("locales");
		if (localesValue == null || localesValue.isEmpty()) {
			log.warn("'locales' init parameter is empty, the default encoding will be used");
		} else {
			List<String> locales = new ArrayList<>();
			StringTokenizer st = new StringTokenizer(localesValue);
			while (st.hasMoreTokens()) {
				String localeName = st.nextToken();
				locales.add(localeName);
			}							
			
			log.debug("Application attribute set: locales --> {}", locales);
			servletContext.setAttribute("locales", locales);
		}		
		
		log.debug("I18N subsystem initialization finished");
	}

	private void initLog4J(ServletContext servletContext) {
		System.out.println("Log4J initialization started");
		try {
			ConfigurationSource source = new ConfigurationSource(
					new FileInputStream(servletContext.getRealPath("WEB-INF/log4j2.properties")));
			Configurator.initialize(this.getClass().getClassLoader(), source);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("Log4J initialization finished");
	}

}
