package com.taxi.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.taxi.entity.Account;
import com.taxi.entity.Car;
import com.taxi.entity.CarState;
import com.taxi.entity.CarType;

public interface CarDao {
	List<Car> getFreeCarsByPlaces(String type) throws SQLException;

	CarType getCarTypeById(int id) throws SQLException;

	List<Car> chooseCars(List<Car> cars, int places);

	Map<String, Integer> getAllFreeCarByType() throws SQLException;

	Car getCarByTypePlaces(CarType type, int places) throws SQLException;

	List<Car> getCarsInOrder(int id) throws SQLException;

	CarState getCarStateByAccount(Account account) throws SQLException;
	
	Car getCarByAccountId(Account account) throws SQLException;

	void changeStateId(CarState state, Car car) throws SQLException;

	void createCar(Car car, Connection con) throws SQLException;

	String getCurrentPosition(Car car);

}
