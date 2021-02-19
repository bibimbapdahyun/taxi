package com.taxi.dao;

import java.sql.SQLException;
import java.util.List;

import com.taxi.entity.TripState;

public interface TripStateDao {

	List<TripState> getAllTripState() throws SQLException;

	TripState getStateById(int stateId) throws SQLException;

}
