package command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.taxi.entity.Account;
import com.taxi.entity.CarType;
import com.taxi.entity.Order;
import com.taxi.web.command.Path;
import com.taxi.web.command.order.SetOrder;

public class OrderTest {
	HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
	HttpServletResponse mockResponse= Mockito.mock(HttpServletResponse.class);
	HttpSession mockSession = Mockito.mock(HttpSession.class);
	Account account = Mockito.mock(Account.class);
	CarType business = Mockito.mock(CarType.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		account.setLogin("380666658673");
//		Role role = new Role();
//		Mockito.when(account.getLogin()).thenReturn("380666658673");
//		
//		Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
//
//		Mockito.when(mockSession.getAttribute("account")).thenReturn(account);
//
//		Mockito.when(mockRequest.getParameter("places")).thenReturn("5");
//		Mockito.when(mockRequest.getParameter("type")).thenReturn("business");
		
		
		
		
	}
	

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() {
		Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
		Mockito.when(mockSession.getAttribute("account")).thenReturn(account);
		Mockito.when(mockRequest.getParameter("from")).thenReturn("tudanga");
		Mockito.when(mockRequest.getParameter("to")).thenReturn("kudanga");
		Mockito.when(mockRequest.getParameter("places")).thenReturn("5");
		Mockito.when(mockRequest.getParameter("type")).thenReturn("business");
		SetOrder setOrder = new SetOrder();
		String forward = null;
		try {
			forward = setOrder.execute(mockRequest, mockResponse);
		} catch (IOException | ServletException e) {
			fail("Exception");
			e.printStackTrace();
		}
		
		String expected = Path.GET_LOGIN_FORM_CMD;
		assertEquals(expected, forward);
		
	}

}
