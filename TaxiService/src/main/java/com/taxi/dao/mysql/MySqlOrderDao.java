package com.taxi.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.dao.AccountDao;
import com.taxi.dao.CarDao;
import com.taxi.dao.CarTypeDao;
import com.taxi.dao.DAOFactory;
import com.taxi.dao.OrderDao;
import com.taxi.dao.TripStateDao;
import com.taxi.entity.Account;
import com.taxi.entity.Car;
import com.taxi.entity.Order;

public class MySqlOrderDao implements OrderDao {

	private static final String SELECT_TRIPS_SORT_DESC = "SELECT * FROM trip ORDER BY price DESC LIMIT ? OFFSET ?;";
	private static final String SELECT_TRIPS_SORT_ASC = "SELECT * FROM trip ORDER BY price LIMIT ? OFFSET ?;";
	private static OrderDao instance;
	private static AccountDao adao = DAOFactory.getInstance().getAccountDao();
	private static CarTypeDao ctdao = DAOFactory.getInstance().getTypeDao();
	private static TripStateDao tsdao = DAOFactory.getInstance().getTripStateDao();
	private static CarDao cdao = DAOFactory.getInstance().getCarDao();

	private static final String INSERT_ORDER_TO_DB = "INSERT INTO trip (id, start, finish, create_date, price, account_id, people, car_type_id, state_id)"
			+ "VALUES (DEFAULT, ?, ?, DEFAULT, ?, ?, ?,?, DEFAULT)";
	private static final Logger log = LogManager.getLogger(MySqlOrderDao.class);

	private MySqlOrderDao() {
		// nothing
	}

	public static synchronized OrderDao getInstance() {
		if (instance == null) {
			instance = new MySqlOrderDao();
		}
		return instance;
	}

	@Override
	public List<Order> getAllOrder() {
		return null;
	}

	@Override
	public Order getOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createOrder(Order order, List<Car> cars) throws SQLException {
		Connection con = MySqlDAOFactory.getConnection();
		try {
			con.setAutoCommit(false);
			con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			setOrder(con, order);
			for (Car car : cars) {
				addCar(con, car, order);
				carChangeState(con, car);
			}
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
			log.error("error in createOreder#method");
			throw e;
		} finally {
			con.close();
		}

	}

	private void carChangeState(Connection con, Car car) throws SQLException {
		try (PreparedStatement st = con.prepareStatement(
				"UPDATE car SET car.state_id=(SELECT id FROM state WHERE state='intrip') WHERE car.id=?")) {
			st.setInt(1, car.getId());
			st.executeUpdate();
		}
	}

	private void setOrder(Connection con, Order order) throws SQLException {
		try (PreparedStatement st = con.prepareStatement(INSERT_ORDER_TO_DB, Statement.RETURN_GENERATED_KEYS)) {
			int k = 1;
			st.setString(k++, order.getStart());
			st.setString(k++, order.getFinish());
			st.setInt(k++, order.getPrice());
			st.setInt(k++, order.getAccount().getId());
			st.setInt(k++, order.getPlaces());
			st.setInt(k++, order.getType().getId());
			if (st.executeUpdate() > 0) {
				try (ResultSet rs = st.getGeneratedKeys()) {
					if (rs.next()) {
						order.setId(rs.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			log.error("error in setOrder");
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void setCarForOrder(int carId, int orderId) throws SQLException {
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("INSERT INTO trip_car (car_id, trip_id) VALUES (?,?)")) {
			st.setInt(1, carId);
			st.setInt(2, orderId);
			st.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	private void addCar(Connection con, Car car, Order order) throws SQLException {
		try (PreparedStatement st = con.prepareStatement("INSERT INTO trip_car (car_id, trip_id) VALUES (?,?)")) {
			st.setInt(1, car.getId());
			st.setInt(2, order.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			log.trace("error int addCar");
			throw e;
		}
	}

	private static final String SELECT_ORDERS_BY_LIMIT_OFFSET = "SELECT * FROM trip ORDER BY id LIMIT ? OFFSET ?";

	@Override
	public List<Order> getOrderCount(int limit, int offset) throws SQLException {
		List<Order> orders = new ArrayList<>();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(SELECT_ORDERS_BY_LIMIT_OFFSET)) {
			st.setInt(1, limit);
			st.setInt(2, offset);
			try (ResultSet rs = st.executeQuery()) {
				mapTrips(orders, rs);
			}
		}
		return orders;
	}

	private static final String SELECT_ORDERS_BY_ACCOUNT_LIMIT_OFFSET = "SELECT * FROM trip WHERE account_id=? ORDER BY id LIMIT ? OFFSET ?";

	@Override
	public List<Order> getOrdersByAccount(Account account, int limit, int offset) throws SQLException {
		List<Order> orders = new ArrayList<>();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(SELECT_ORDERS_BY_ACCOUNT_LIMIT_OFFSET);) {
			st.setInt(1, account.getId());
			st.setInt(2, limit);
			st.setInt(3, offset);
			try (ResultSet rs = st.executeQuery()) {
				mapTrips(orders, rs);
			}
		}
		return orders;
	}

	private static final String SELECT_FROM_TRIP_BY_START_FINISH_DATE = "SELECT * FROM trip WHERE create_date BETWEEN ? AND ? ORDER BY create_date LIMIT ? OFFSET ?";

	@Override
	public List<Order> getOrdersByDate(Date startDate, Date endDate, int limit, int offset) throws SQLException {
		List<Order> orders = new ArrayList<>();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(SELECT_FROM_TRIP_BY_START_FINISH_DATE)) {
			st.setDate(1, new java.sql.Date(startDate.getTime()));
			st.setDate(2, new java.sql.Date(endDate.getTime()));
			st.setInt(3, limit);
			st.setInt(4, offset);
			try (ResultSet rs = st.executeQuery()) {
				mapTrips(orders, rs);
			}
		}
		return orders;
	}

	@Override
	public List<Order> getOrderByPrice(boolean asc, int limit, int offset) throws SQLException {
		List<Order> orders = new ArrayList<>();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(asc ? SELECT_TRIPS_SORT_ASC : SELECT_TRIPS_SORT_DESC);) {
			st.setInt(1, limit);
			st.setInt(2, offset);
			try (ResultSet rs = st.executeQuery()) {
				mapTrips(orders, rs);
			}
		}
		return orders;
	}

	private void mapTrips(List<Order> orders, ResultSet rs) throws SQLException {
		while (rs.next()) {
			Order order = mapOrder(rs);
			orders.add(order);
			log.debug("order: {}", order);
		}
	}

	private Order mapOrder(ResultSet rs) throws SQLException {
		Order order = new Order();
		int k = 1;
		order.setId(rs.getInt(k++));
		order.setStart(rs.getString(k++));
		order.setFinish(rs.getString(k++));
		order.setData(rs.getTimestamp(k++));
		order.setPrice(rs.getInt(k++));
		order.setAccount(adao.getAccountById(rs.getInt(k++)));
		order.setPlaces(rs.getInt(k++));
		order.setType(ctdao.getTypeById(rs.getInt(k++)));
		order.settState(tsdao.getStateById(rs.getInt(k)));
		return order;
	}

	private static final String SELECT_TRIP_SORT_ASC_DATE = "SELECT * FROM trip ORDER BY create_date LIMIT ? OFFSET ?";
	private static final String SELECT_TRIP_SORT_DESC_DATE = "SELECT * FROM trip ORDER BY create_date DESC LIMIT ? OFFSET ?";

	@Override
	public List<Order> getOrderByDate(boolean asc, int limit, int offset) throws SQLException {
		List<Order> orders = new ArrayList<>();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con
						.prepareStatement(asc ? SELECT_TRIP_SORT_ASC_DATE : SELECT_TRIP_SORT_DESC_DATE);) {
			st.setInt(1, limit);
			st.setInt(2, offset);
			try (ResultSet rs = st.executeQuery()) {
				mapTrips(orders, rs);
			}
		}
		return orders;
	}

	private static final String SELECT_TRIP_BY_CAR_AND_STATE = "SELECT trip.* FROM trip, trip_car WHERE trip_car.car_id=? AND trip_car.trip_id=trip.id AND trip.state_id=2;";

	@Override
	public Order getActualOrderCar(Car car) throws SQLException {
		Order order = new Order();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(SELECT_TRIP_BY_CAR_AND_STATE)) {
			st.setInt(1, car.getId());
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					order = mapOrder(rs);
				}
			}
		}
		return order;
	}

	private static final String FREE_CARS = "UPDATE car SET car.state_id=3 WHERE car.id=?";

	private static final String FINISH_ORDER = "UPDATE trip SET trip.state_id=(SELECT id FROM trip_state WHERE state='finished') WHERE trip.id=?;";

	@Override
	public void finishOrder(Order order) throws SQLException {
		Connection con = MySqlDAOFactory.getConnection();
		try {
			con.setAutoCommit(false);
			con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			List<Car> cars = cdao.getCarsInOrder(order.getId());
			finish(order, con, FINISH_ORDER);
			for (Car car : cars) {
				freeCar(car, con);
			}
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.close();
		}
	}

	private void freeCar(Car car, Connection con) throws SQLException {
		try (PreparedStatement st = con.prepareStatement(FREE_CARS);) {
			st.setInt(1, car.getId());
			st.executeUpdate();
		}
	}

	private void finish(Order order, Connection con, String query) throws SQLException {
		try (PreparedStatement st = con.prepareStatement(query);) {
			st.setInt(1, order.getId());
			st.executeUpdate();
		}
	}

	private static final String SELECT_TRIP_DESC_DRIVER = "SELECT trip.* FROM trip, trip_car WHERE trip_car.car_id=? AND trip.state_id!=2 AND trip_car.trip_id=trip.id ORDER BY create_date DESC LIMIT ? OFFSET ?;";

	@Override
	public List<Order> getOrderByDriver(Car car, int limit, int offset) throws SQLException {
		List<Order> orders = new ArrayList<>();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(SELECT_TRIP_DESC_DRIVER);) {
			st.setInt(1, car.getId());
			st.setInt(2, limit);
			st.setInt(3, offset);
			try (ResultSet rs = st.executeQuery()) {
				mapTrips(orders, rs);
			}
		}
		return orders;
	}

	private static final String SELECT_COUNT_OF_ORDER = "SELECT COUNT(*) COUNT FROM trip;";

	@Override
	public int getCountOfOrders() throws SQLException {
		int count = 0;
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(SELECT_COUNT_OF_ORDER);
				ResultSet rs = st.executeQuery()) {
			if (rs.next()) {
				count = rs.getInt(1);
			}
		}
		return count;
	}

	private static final String SELECT_COUNT_OF_ORDER_BY_ACCOUNT_ID = "SELECT COUNT(*) COUNT FROM trip WHERE account_id=?;";

	@Override
	public int getCountOfOrdersByAccount(int id) throws SQLException {
		int count = 0;
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(SELECT_COUNT_OF_ORDER_BY_ACCOUNT_ID)) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					count = rs.getInt(1);
				}
			}
		}
		return count;
	}

	private static final String SELECT_COUNT_OF_ORDERS_BY_START_FINISH_DATE = "SELECT COUNT(*) COUNT FROM trip WHERE create_date BETWEEN ? AND ?";

	@Override
	public int getCountOfOrdersByDate(Date start, Date finish) throws SQLException {
		int count = 0;
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(SELECT_COUNT_OF_ORDERS_BY_START_FINISH_DATE);) {
			st.setDate(1, new java.sql.Date(start.getTime()));
			st.setDate(2, new java.sql.Date(finish.getTime()));
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					count = rs.getInt(1);
				}
			}
		}
		return count;
	}
	
	private static final String SELECT_COUNT_OF_ORDERS_BY_CAR = "SELECT COUNT(*) FROM trip_car,trip WHERE trip.id=trip_car.trip_id AND trip_car.car_id=?";

	@Override
	public int getCountOfOrdersByCar(Car car) throws SQLException {
		int count = 0;
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(SELECT_COUNT_OF_ORDERS_BY_CAR);) {
			st.setInt(1, car.getId());
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					count = rs.getInt(1);
				}
			}
		}
		return count;
	}
}
