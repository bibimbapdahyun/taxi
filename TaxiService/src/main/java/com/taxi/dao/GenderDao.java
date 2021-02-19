package com.taxi.dao;

import java.sql.SQLException;
import java.util.List;

import com.taxi.entity.Gender;

public interface GenderDao {
	
	List<Gender> getAllGenders() throws SQLException;

	Gender getGenderById(int id) throws SQLException;
}
