package com.taxi.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.web.command.Command;
import com.taxi.web.command.CommandContainer;
import com.taxi.web.command.Path;

@WebServlet("/controller")
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 2423353715955164816L;

	private static final Logger log = LogManager.getLogger(Controller.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String forward = Path.GET_ERROR_PAGE;
		try {
			log.debug("Controller starts");

			// extract command name from the request
			String commandName = request.getParameter("command");
			log.trace("Request parameter: command --> {}", commandName);

			// obtain command object by its name
			Command command = CommandContainer.get(commandName);
			log.trace("Obtained command --> {} ", command);
			// execute command and get forward address
			forward = command.execute(request, response);

			log.trace("Forward address --> {}", forward);

			log.debug("Controller finished, now go to forward address --> {}", forward);

			// if the forward address is not null go to the address
			if (forward != null) {
				request.getRequestDispatcher(forward).forward(request, response);
			}
		} catch (ServletException | IOException e) {
			log.error("Error in doPost");
			request.getSession().setAttribute("errorMessage", "Something was wrong");
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String forward = Path.GET_ERROR_PAGE;
		try {
			log.debug("Controller starts");

			// extract command name from the request
			String commandName = request.getParameter("command");
			log.trace("Request parameter: command --> {}", commandName);

			// obtain command object by its name
			Command command = CommandContainer.get(commandName);
			log.trace("Obtained command --> {} ", command);
			// execute command and get forward address
			forward = command.execute(request, response);

			log.trace("Forward address --> {}", forward);

			log.debug("Controller finished, now go to forward address --> {}", forward);

			// if the forward address is not null go to the address
			response.sendRedirect(forward);
		} catch (IOException e) {
			log.error("Error in doPost");
			request.getSession().setAttribute("errorMessage", "Something was wrong");
			e.printStackTrace();
		}
	}

}