package com.taxi.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.dao.CarTypeDao;
import com.taxi.entity.CarType;

public class MySqlCarTypeDao implements CarTypeDao {

	private static CarTypeDao instance;
	private Logger log = LogManager.getLogger(MySqlCarDao.class);

	private MySqlCarTypeDao() {
		// nothing
	}

	public static synchronized CarTypeDao getInstance() {
		if (instance == null) {
			instance = new MySqlCarTypeDao();
		}
		return instance;
	}

	@Override
	public CarType getTypeParameter(String type) throws SQLException {
		CarType ct = new CarType();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM car_type WHERE type=?");) {
			st.setString(1, type);
			try (ResultSet rs = st.executeQuery()) {
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
	public List<CarType> getAllTypes() throws SQLException {
		List<CarType> lct = new ArrayList<>();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM car_type");
				ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				CarType ct = new CarType();
				int k = 1;
				ct.setId(rs.getInt(k++));
				ct.setType(rs.getString(k++));
				ct.setStartPrice(rs.getInt(k++));
				ct.setPricePerKm(rs.getInt(k));
				lct.add(ct);
			}
		}
		return lct;
	}

	@Override
	public CarType getTypeById(int id) throws SQLException {
		CarType ct = new CarType();
		try(Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM car_type WHERE id=?")){
			st.setInt(1, id);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					ct.setId(rs.getInt(1));
					ct.setType(rs.getString(2));
					ct.setStartPrice(rs.getInt(3));
					ct.setPricePerKm(rs.getInt(4));
				}
			}
		}
		return ct;
	}

}
