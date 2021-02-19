package com.taxi.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.taxi.dao.CarStateDao;
import com.taxi.entity.CarState;
import com.taxi.entity.CarType;

public class MySqlStateDao implements CarStateDao {

	private static CarStateDao instance = new MySqlStateDao();

	private MySqlStateDao() {
		// nothing
	}

	public static synchronized CarStateDao getInstance() {
		if (instance == null) {
			instance = new MySqlStateDao();
		}
		return instance;
	}

	@Override
	public CarState getStateById(int id) throws SQLException {
		CarState state = new CarState();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM state WHERE id=?");) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery();) {

				if (rs.next()) {
					int k = 1;
					state.setId(rs.getInt(k++));
					state.setName(rs.getString(k++));
				}
			}
		}
		return state;
	}

	@Override
	public List<CarState> getAllCarState() throws SQLException {
		List<CarState> lcs = new ArrayList<>();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM state");
				ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				CarState cs = new CarState();
				int k = 1;
				cs.setId(rs.getInt(k++));
				cs.setName(rs.getString(k++));
				lcs.add(cs);
			}
		}
		return lcs;
	}

}
