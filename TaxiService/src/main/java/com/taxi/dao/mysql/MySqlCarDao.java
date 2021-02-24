package com.taxi.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.dao.AccountDao;
import com.taxi.dao.CarDao;
import com.taxi.dao.CarStateDao;
import com.taxi.dao.CarTypeDao;
import com.taxi.dao.DAOFactory;
import com.taxi.entity.Account;
import com.taxi.entity.Car;
import com.taxi.entity.CarState;
import com.taxi.entity.CarType;

public class MySqlCarDao implements CarDao {
	private static CarDao instanse;
	private CarStateDao sdao = MySqlStateDao.getInstance();
	private CarTypeDao ctdao = MySqlCarTypeDao.getInstance();
	private AccountDao adao = MySqlAccountDao.getInstance();

	private static final Logger log = LogManager.getLogger(MySqlCarDao.class);

	private MySqlCarDao() {
		// nothing
	}

	public static synchronized CarDao getInstanse() {
		if (instanse == null) {
			instanse = new MySqlCarDao();
		}
		return instanse;
	}

	@Override
	public CarType getCarTypeById(int id) throws SQLException {
		CarType ct = new CarType();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM car_type WHERE id=?");) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery();) {
				if (rs.next()) {
					int k = 1;
					ct.setId(rs.getInt(k++));
					ct.setType(rs.getString(k++));
					ct.setStartPrice(rs.getInt(k++));
					ct.setPricePerKm(rs.getInt(k));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return ct;
	}

	@Override
	public List<Car> getFreeCarsByPlaces(String type) throws SQLException {
		List<Car> cars = new ArrayList<>();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(
						"SELECT * FROM car WHERE state_id=3 AND car_type_id=(SELECT id FROM car_type WHERE type=?)");) {
			st.setString(1, type);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					Car car = new Car();
					int k = 1;
					car.setId(rs.getInt(k++));
					car.setCarNumber(rs.getString(k++));
					car.setMark(rs.getString(k++));
					car.setPlaces(rs.getInt(k++));
					car.setAccount(adao.getAccountById(rs.getInt(k++)));
					car.setState(sdao.getStateById(rs.getInt(k++)));
					car.setCarType(instanse.getCarTypeById(rs.getInt(k)));
					cars.add(car);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return cars;
	}

	@Override
	public List<Car> chooseCars(List<Car> cars, int places) {
		log.trace("chooseCars start work");
		if (cars.isEmpty()) {
			return cars;
		}
		log.debug("Current cars: {}", cars);
		List<Car> result = getSomeCars(cars, places);
		log.debug("Current result: {}", result);
		log.trace("chooseCars finish work");
		return result;
	}

	/**
	 * @author bibimbap
	 * 
	 * @param cars
	 * @param places
	 * @return Return some cars, when sum of places in cars equals or more then
	 *         places parameter.
	 */

	private List<Car> getSomeCars(List<Car> cars, int places) {
		int sum = 0;
		List<Car> result = new LinkedList<>();
		for (Car car : cars) {
			if (sum >= places) {
				log.debug("Find cars for trip {}", result);
				break;
			}
			sum += car.getPlaces();
			result.add(car);
		}
		if(sum < places) {
			return new LinkedList<>();
		}
		return result;
	}

	/**
	 * @author bibimbap
	 * 
	 *         Возврощает некоторое число машин наиболее подходищих по размеру.
	 * 
	 *         Нужно протестировать.
	 * 
	 */

	/*
	 * private List<Car> getSomeCars(List<Car> cars, int places) { List<Car> actual
	 * = new ArrayList<>(); int sum = 0; int count = 2; int searchPlaces = places;
	 * while (sum < places) { sum = 0; actual = new ArrayList<>(); if (places %
	 * count != 0) { searchPlaces = (places / count) + 1; } else { searchPlaces =
	 * places / count; } for (Car car : cars) { if (car.getPlaces() >= searchPlaces)
	 * { actual.add(car); sum += car.getPlaces(); if (sum > places || actual.size()
	 * == count) { break; } } } count++; if (searchPlaces <= 1) { actual = new
	 * ArrayList<>(); break; } } return actual; }
	 */

	@Override
	public Map<String, Integer> getAllFreeCarByType() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(
						"SELECT car_type.type, COUNT(*) count FROM car,car_type WHERE car.state_id=3 AND car_type_id=car_type.id AND places>=5 GROUP BY car_type.id;");
				ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				map.put(rs.getString(1), rs.getInt(2));
			}
		} catch (SQLException e) {
			log.trace(e.getMessage());
			throw e;
		}
		return map;
	}

	@Override
	public Car getCarByTypePlaces(CarType type, int places) throws SQLException {
		Car car = new Car();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con
						.prepareStatement("SELECT * FROM car WHERE places>=? AND car_type_id=? AND state_id=3");) {
			st.setInt(1, places);
			st.setInt(2, type.getId());
			try (ResultSet rs = st.executeQuery();) {
				if (rs.next()) {
					int k = 1;
					car.setId(rs.getInt(k++));
					car.setCarNumber(rs.getString(k++));
					car.setMark(rs.getString(k++));
					car.setPlaces(rs.getInt(k++));
					car.setAccount(adao.getAccountById(rs.getInt(k++)));
					car.setState(sdao.getStateById(rs.getInt(k++)));
					car.setCarType(instanse.getCarTypeById(rs.getInt(k)));
				}
			}
		} catch (SQLException e) {
			log.trace(e.getMessage());
			throw e;
		}
		return car;
	}

	@Override
	public List<Car> getCarsInOrder(int id) throws SQLException {
		List<Car> cars = new ArrayList<>();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(
						"SELECT car.* FROM car,trip_car WHERE trip_car.trip_id=? AND trip_car.car_id=car.id")) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery()) {
				while (rs.next()) {
					Car car = new Car();
					int k = 1;
					car.setId(rs.getInt(k++));
					car.setCarNumber(rs.getString(k++));
					log.debug("carNumber: {}", car.getCarNumber());
					car.setMark(rs.getString(k++));
					car.setPlaces(rs.getInt(k++));
					
					log.debug("carMark: {}", car.getMark());
					cars.add(car);
				}
			}
		}
		return cars;
	}

	private static final String SELECT_CAR_STATE_BY_ACCOUNT = "SELECT * FROM car_state ";

	@Override
	public CarState getCarStateByAccount(Account account) throws SQLException {
		CarState ct = new CarState();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(SELECT_CAR_STATE_BY_ACCOUNT)) {

		}

		return ct;
	}

	private static final String SELECT_CAR_BY_ACCOUNT_ID = "SELECT * FROM car WHERE account_id=?";

	@Override
	public Car getCarByAccountId(Account account) throws SQLException {
		Car car = new Car();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(SELECT_CAR_BY_ACCOUNT_ID)) {
			st.setInt(1, account.getId());
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					mapCar(account, car, rs);
				}
			}
		}
		return car;
	}

	private void mapCar(Account account, Car car, ResultSet rs) throws SQLException {
		int k = 1;
		car.setId(rs.getInt(k++));
		car.setCarNumber(rs.getString(k++));
		car.setMark(rs.getString(k++));
		car.setPlaces(rs.getInt(k++));
		car.setAccount(account);
		k++;
		car.setState(sdao.getStateById(rs.getInt(k++)));
		car.setCarType(ctdao.getTypeById(rs.getInt(k)));
	}

	private static final String UPDATE_CAR_STATE = "UPDATE car SET car.state_id=? WHERE car.id=?;";

	@Override
	public void changeStateId(CarState state, Car car) throws SQLException {
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(UPDATE_CAR_STATE);) {
			st.setInt(1, state.getId());
			st.setInt(2, car.getId());
			st.executeUpdate();
		}

	}

	private static final String INSERT_CAR = "INSERT INTO car (id, car_number, mark, places, account_id, state_id,car_type_id) VALUES (DEFAULT, ?, ?, ?, ?, DEFAULT, ?);";

	@Override
	public void createCar(Car car, Connection con) throws SQLException {
		try (PreparedStatement st = con.prepareStatement(INSERT_CAR, PreparedStatement.RETURN_GENERATED_KEYS)) {
			int k = 1;
			st.setString(k++, car.getCarNumber());
			st.setString(k++, car.getMark());
			st.setInt(k++, car.getPlaces());
			st.setInt(k++, car.getAccount().getId());
			st.setInt(k++, car.getCarType().getId());
			if (st.executeUpdate() > 0) {
				try (ResultSet rs = st.getGeneratedKeys()) {
					if (rs.next()) {
						car.setId(rs.getInt(1));
					}
				}
			}
		}

	}

	@Override
	public String getCurrentPosition(Car car) {
		return "Sothing get from db";
	}
}
