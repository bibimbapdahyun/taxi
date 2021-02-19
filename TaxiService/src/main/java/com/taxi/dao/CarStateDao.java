package com.taxi.dao;

import java.sql.SQLException;
import java.util.List;

import com.taxi.entity.CarState;

public interface CarStateDao {
	CarState getStateById(int id) throws SQLException;

	List<CarState> getAllCarState() throws SQLException;

}
