package com.taxi.dao;

import java.util.Date;
import java.sql.SQLException;
import java.util.List;

import com.taxi.entity.Account;
import com.taxi.entity.Car;
import com.taxi.entity.Order;

public interface OrderDao {
	List<Order> getAllOrder();

	Order getOrder();

	void createOrder(Order order, List<Car> cars) throws SQLException;

	void setCarForOrder(int carId, int orderId) throws SQLException;
//	void addCarsToOrder(Order order, List<Car> cars) throws SQLException;

	List<Order> getOrderCount(int pageSize, int page) throws SQLException;

	List<Order> getOrdersByAccount(Account account, int limit, int offset) throws SQLException;

	List<Order> getOrdersByDate(Date startDate, Date endDate, int limit, int offset ) throws SQLException;

	List<Order> getOrderByPrice(boolean asc, int limit, int offset)throws SQLException;

	List<Order> getOrderByDate(boolean asc, int limit, int offset) throws SQLException;

	Order getActualOrderCar(Car car) throws SQLException;

	void finishOrder(Order order) throws SQLException;

	List<Order> getOrderByDriver(Car car, int limit, int offset) throws SQLException;

	int getCountOfOrders()throws SQLException;

	int getCountOfOrdersByAccount(int id) throws SQLException;

	int getCountOfOrdersByDate(Date start, Date finish) throws SQLException;

	int getCountOfOrdersByCar(Car car) throws SQLException;
}
