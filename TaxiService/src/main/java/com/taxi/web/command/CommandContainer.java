package com.taxi.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.web.command.login.Login;
import com.taxi.web.command.login.Logout;
import com.taxi.web.command.login.get.GetLoginForm;
import com.taxi.web.command.manager.CreateAccount;
import com.taxi.web.command.manager.FilterByAccount;
import com.taxi.web.command.manager.FilterByDate;
import com.taxi.web.command.manager.RegisterAccount;
import com.taxi.web.command.manager.SortByDate;
import com.taxi.web.command.manager.SortByPrice;
import com.taxi.web.command.manager.get.GetConfigPage;
import com.taxi.web.command.manager.get.GetFilterByAccount;
import com.taxi.web.command.manager.get.GetRegisterAccount;
import com.taxi.web.command.manager.get.GetRegisterAccountForm;
import com.taxi.web.command.manager.get.GetRegisterAccountFormJsp;
import com.taxi.web.command.manager.get.GetStatistics;
import com.taxi.web.command.manager.get.GetStatisticsOrder;
import com.taxi.web.command.manager.get.GetStatisticsPage;
import com.taxi.web.command.order.CancelOrder;
import com.taxi.web.command.order.CheckOutBigOrder;
import com.taxi.web.command.order.CreateOrder;
import com.taxi.web.command.order.SetOrder;
import com.taxi.web.command.order.get.GetOrderForm;
import com.taxi.web.command.order.get.GetOrderValidateForm;
import com.taxi.web.command.order.get.GetReceipt;
import com.taxi.web.command.registration.RegisterUser;
import com.taxi.web.command.registration.get.GetRegisterForm;

public class CommandContainer {

	private static final Logger log = LogManager.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new TreeMap<>();

	static {
		// order commands
		
		// getOrderForm
		commands.put("makeOrder", new GetOrderForm());
		// set order info and get confirm form
		commands.put("setOrder", new SetOrder());
		// get order validate form
		commands.put("validateOrder", new GetOrderValidateForm());
		// insert order in db and get info from created order
		commands.put("createOrder", new CreateOrder());
		// get info about registered order
		commands.put("getReceipt", new GetReceipt());
		// cancel order and clear session
		commands.put("cancelOrder", new CancelOrder());
		// 
		// back to main page
		commands.put("backToIndex", new BackToIndex());
		// if order too big send message for manager and manager need to call by number
		// TODO implement command
		commands.put("checkOutBigOrder", new CheckOutBigOrder());

		// registration commands
		// get registration form
		commands.put("getRegisterForm", new GetRegisterForm());
		// registration user (put all needed information in form and insert to db) 
		commands.put("registerUser", new RegisterUser());

		// login command
		// add to session account
		commands.put("login", new Login());
		// session invalidate 
		commands.put("logout", new Logout());
		// Get login form for user
		commands.put("getLoginForm", new GetLoginForm());

		// get main page
		commands.put("getIndexJsp", new GetIndexJsp());
		// admin commands
		commands.put("getConfigPage", new GetConfigPage());
		commands.put("registerAccount", new GetRegisterAccount());
		commands.put("getRegisterAccountForm", new GetRegisterAccountForm());
		commands.put("createAccount", new CreateAccount());
		
		commands.put("getRegisterAccountFormJsp", new GetRegisterAccountFormJsp());
		commands.put("getStatistics", new GetStatistics());
		commands.put("getFilterByAccount", new GetFilterByAccount());
		commands.put("getStatisticsPage", new GetStatisticsPage());
		commands.put("filterByAccount", new FilterByAccount());
		commands.put("filterByDate", new FilterByDate());
		commands.put("sortByPrice", new SortByPrice());
		commands.put("getCarForm", new GetCarForm());
		commands.put("registerCar", new RegisterCar());
		
		// driver
		commands.put("getDriverActualOrder", new GetDriverActualOrder());
		commands.put("getDriverConfirmOrder", new GetDriverConfirmOrder());
		commands.put("finishOrder", new FinishOrder());
		commands.put("changeAccountState", new GetAccountState());
		commands.put("changeState", new ChangeAccountState());
		
		
		
		// manager command
		commands.put("getStatisticsOrder", new GetStatisticsOrder());
		commands.put("sortByDate", new SortByDate());
		


		commands.put("updateLocale", new UpdateLocale());
		commands.put("noCommand", new NoCommand());
		
		commands.put("getErrorPage", new GetErrorPage());
		
		log.debug("Command container was successfully initialized");
		log.trace("Number of commands --> {}", commands.size());
	}

	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			log.trace("Command not found, name --> {}", commandName);
			return commands.get("noCommand");
		}

		return commands.get(commandName);
	}

}