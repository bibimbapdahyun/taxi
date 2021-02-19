package com.taxi.dao;

import java.sql.SQLException;
import java.util.List;

import com.taxi.entity.CarType;

public interface CarTypeDao{

	CarType getTypeParameter(String type) throws SQLException;
	List<CarType> getAllTypes() throws SQLException;
	CarType getTypeById(int id) throws SQLException;
	
}
