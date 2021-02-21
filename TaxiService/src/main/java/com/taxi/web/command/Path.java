package com.taxi.web.command;

public interface Path {

	// command 
	// order
	String GET_ORDER_RECEIPT_CMD = "controller?command=getReceipt";
	String MAKE_ORDER_CMD = "controller?command=makeOrder";
	String VALIDATE_ORDER_CMD = "controller?command=validateOrder";
	String SET_ORDER_CMD = "controller?command=setOrder";
	// registration
	String GET_REGISTER_FORM_JSP_CMD = "controller?command=getRegisterForm";
	String GET_INDEX_JSP_CMD = "controller?command=getIndexJsp";
	//Login
	String GET_LOGIN_FORM_CMD = "controller?command=getLoginForm";
	//admin
	String GET_STATISTICS_PAGE = "controller?command=getStatisticsPage";
	String GET_FILTER_BY_ACCOUNT = "controller?command=getFilterByAccountJsp";
	String GET_REGISTER_ACCOUNT_FORM = "controller?command=getRegisterAccountFormJsp";
	String GET_DRIVER_CONFIRM_ORDER = "controller?command=getDriverConfirmOrder";
	String GET_DRIVER_ACTUAL_ORDER = "controller?command=getDriverActualOrder";
	String CHANGE_ACCOUNT_STATE_CMD = "controller?command=changeAccountState";
	String GET_CHANGE_STATE_CMD = "controller?command=getChangeState";

	String GET_CAR_FORM_CMD = "controller?command=getCarForm";
	
	String GET_ERROR_PAGE = "controller?command=getErrorPage";
	// jsp path
	// order
	String GET_ORDER_FORM_JSP = "/WEB-INF/view/order/orderForm.jsp";
	String GET_ORDER_VALIDATE_FORM_JSP = "/WEB-INF/view/order/validateOrder.jsp";
	String GET_RECEIPT_JSP = "/WEB-INF/view/order/receipt.jsp";
	//registration
	String GET_REGISTER_FORM_JSP = "/WEB-INF/view/registration/registerForm.jsp";
	//login
	String LOGIN_FORM_JSP = "/WEB-INF/view/login/loginForm.jsp";
	// admin
	String CAR_FORM_JSP = "/WEB-INF/view/admin/carForm.jsp";
	String ADMIN_CONFIG_PAGE = "/WEB-INF/view/admin/configurationPage.jsp";
	String REGISTER_ACCOUNT_JSP = "/WEB-INF/view/admin/registerAccount.jsp";
	String REGISTER_ACCOUNT_FORM_JSP = "/WEB-INF/view/admin/registerAccountForm.jsp";
	
	String GET_DRIVER_USER_PAGE = "/WEB-INF/view/driver/userPage.jsp";
	String DRIVER_CONFIRM_ORDER = "/WEB-INF/view/driver/driverConfirmOrder.jsp";
	
	String GET_STATISTICS_ORDER_JSP = "/WEB-INF/view/statisticsOrder.jsp";
	
	String INDEX_JSP = "/WEB-INF/view/index.jsp";
	
	String SHOW_ERROR_PAGE ="/WEB-INF/view/error.jsp"; 
	

	//==============================================//
	String FILTER_BY_ACCOUNT_JSP = "/WEB-INF/view/admin/filterByAccount.jsp";
	String FILTER_BY_DATE_JSP = "/WEB-INF/view/admin/filterByDate.jsp";
	String SORT_BY_PRICE_JSP = "/WEB-INF/view/admin/sortByPrice.jsp";
	
	String RESPOSE_ORDER = "/WEB-INF/view/responseMessageOrder.jsp";
	String SHOW_MESSAGE_FOR_USER = "/WEB-INF/view/showMessage.jsp";

	// Command
	
	String SHOW_ORDER_CMD = "controller?command=showOrder";
	String CANCEL_ORDER_CMD = "controller?command=cancelOrder";
	String SEND_MESSAGE_TO_ADMIN_CMD = "controller?command=sendMessageForAdmin";
	
}
