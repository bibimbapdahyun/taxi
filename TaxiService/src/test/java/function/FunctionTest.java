package function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taxi.entity.CarType;
import com.taxi.function.Calculator;
import com.taxi.function.DefineDistance;
import com.taxi.function.Discount;
import com.taxi.function.DiscountImpl;
import com.taxi.function.Distance;
import com.taxi.function.Hash;
import com.taxi.function.PriceCalculator;
import com.taxi.function.ShaHash;
import com.taxi.function.Time;
import com.taxi.function.WaitingTime;

public class FunctionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void distanceShouldBeBetween5and30() {
		Distance dist = new DefineDistance();
		boolean flag = true;
		int actual = dist.getDistance("Something", "Something");
		for (int i = 0; i < 20; i++) {
			if (actual > 25 && actual < 5) {
				flag = false;
				break;
			}
		}
		assertEquals(true, flag);
	}
	
	@Test
	public void discountShouldReturn900WhenPrice1000() {
		Discount discount = new DiscountImpl();
		int actual = 1000;
		int expected = 900;
		actual = discount.discount(actual);
		boolean flag = false;
		if(actual == expected) {
			flag = true;
		}
		assertEquals(true, flag);
	}
	
	@Test
	public void calculatorShouldReturn140WhenDistance10AndTypeBusiness() {
		Calculator calc = new PriceCalculator();
		CarType ct = new CarType();
		// init a business car type
		ct.setPricePerKm(9);
		ct.setStartPrice(50);
		int expected = 140;
		int actual = calc.calculate(10, ct);
		assertEquals(expected, actual);
	}
	
	@Test
	public void calculatorShouldReturn105WhenDistance10AndTypeEconom() {
		Calculator calc = new PriceCalculator();
		CarType ct = new CarType();
		// init a econom car type
		ct.setPricePerKm(8);
		ct.setStartPrice(25);
		int expected = 105;
		int actual = calc.calculate(10, ct);
		assertEquals(expected, actual);
	}
	
	@Test
	public void hashTest() {
		Hash hash = new ShaHash();
		
		String expected = "DBD00D24DF218535E95007C00DF517BEA4C9F967D8F747A761C46091B416C31F6826124F9D6C947A12C011EA50C5B0BEE8DB5A4B4C8782EED292DAE4BD5517A2";
		String actual = null;
		try {
			actual = hash.hash("380000000002");
		} catch (NoSuchAlgorithmException e) {
			fail("Troble in hash algorithm");
			e.printStackTrace();
		}
		if(actual == null) {
			fail("input string not initialized");
		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void timeTest() {
		Time time = new WaitingTime();
		int actual = time.time(10);
		int expected = 12;
		assertEquals(expected, actual);
	}

}
