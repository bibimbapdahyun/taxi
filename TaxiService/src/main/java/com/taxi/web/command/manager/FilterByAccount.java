package com.taxi.web.command.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.dao.AccountDao;
import com.taxi.dao.CarDao;
import com.taxi.dao.DAOFactory;
import com.taxi.dao.OrderDao;
import com.taxi.entity.Account;
import com.taxi.entity.Order;
import com.taxi.web.command.Command;
import com.taxi.web.command.Path;

public class FilterByAccount extends Command {

	private static final String FIND_ACCOUNT = "findAccount";

	private static final String ORDER = "orders";

	private static final String ERROR_MESSAGE = "errorMessage";

	private static final long serialVersionUID = -5816484057478544189L;

	private static OrderDao odao = DAOFactory.getInstance().getOrderDao();
	private static AccountDao adao = DAOFactory.getInstance().getAccountDao();
	private static CarDao cdao = DAOFactory.getInstance().getCarDao();

	private static final int LIMIT = 5;
	private static final Logger log = LogManager.getLogger(FilterByAccount.class);

	/**
	 * The method get all orders by account.
	 */
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		clearSession(request.getSession());
		int offset = Integer.parseInt(request.getParameter("page"));
		HttpSession session = request.getSession();
		request.setAttribute("cmd", request.getParameter("command"));
		request.setAttribute("page", request.getParameter("page"));
		String forward = Path.GET_ERROR_PAGE;
		try {
			Account account;
			if (request.getParameter("login") != null) {
				account = adao.getAccountByLogin(request.getParameter("login"));
				session.setAttribute(FIND_ACCOUNT, account);
			}else {
				account = (Account) session.getAttribute(FIND_ACCOUNT);
			}
			int totalSize = odao.getCountOfOrdersByAccount(account.getId());
			session.setAttribute("pageCount", calculatePageCount(totalSize));
			session.setAttribute("currentPosition", currentPosition(LIMIT, offset));
			List<Order> orders = odao.getOrdersByAccount(account, LIMIT, currentPosition(LIMIT, offset));
			log.debug("Orders: {}", orders);
			if (orders.isEmpty()) {
				request.getSession().setAttribute(ERROR_MESSAGE, "Orders not found");
				forward = Path.GET_STATISTICS_PAGE;
			} else {
				for (Order order : orders) {
					order.setCar(cdao.getCarsInOrder(order.getId()));
				}
				request.getSession().setAttribute(ORDER, orders);
				log.debug("Orders session: {}", request.getSession().getAttribute(ORDER));
				forward = Path.GET_STATISTICS_PAGE;
			}
		} catch (SQLException e) {
			request.getSession().setAttribute(ERROR_MESSAGE, "ERROR!!!");
			e.printStackTrace();

		}
		return forward;
	}

	private int calculatePageCount(int totalSize) {
		if (totalSize % LIMIT == 0) {
			return totalSize / LIMIT;
		}
		return totalSize / LIMIT + 1;
	}
//	private int currentStartPosition(int limit, int offset) {
//		return offset == 1 ? 1 : currentPosition(limit, offset);
//	}

	private int currentPosition(int limit, int offset) {
		return limit * (offset - 1);
	}

	private void clearSession(HttpSession session) {
		session.removeAttribute(ERROR_MESSAGE);
		session.removeAttribute(ORDER);

	}

}
