package com.taxi.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.taxi.dao.TripStateDao;
import com.taxi.entity.CarType;
import com.taxi.entity.TripState;

public class MySqlTripStateDao implements TripStateDao {
	private static TripStateDao instance;

	private MySqlTripStateDao() {
		// nothing
	}

	public static synchronized TripStateDao getInstance() {
		if (instance == null) {
			instance = new MySqlTripStateDao();
		}
		return instance;
	}

	@Override
	public List<TripState> getAllTripState() throws SQLException {
		List<TripState> lts = new ArrayList<>();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM trip_state;");
				ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				TripState ts = new TripState();
				ts.setId(rs.getInt(1));
				ts.setName(rs.getString(2));
				lts.add(ts);
			}
		}
		return lts;
	}

	@Override
	public TripState getStateById(int stateId) throws SQLException {
		TripState state = new TripState();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM trip_state WHERE id=?");
				) {
			st.setInt(1, stateId);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				int k = 1;
				state.setId(rs.getInt(k++));
				state.setName(rs.getString(k));
			}
		}
		return state;
	}

}
