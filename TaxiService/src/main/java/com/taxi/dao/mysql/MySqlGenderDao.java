package com.taxi.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.dao.GenderDao;
import com.taxi.entity.Gender;
import com.taxi.entity.Role;

public class MySqlGenderDao implements GenderDao {
	private static GenderDao instance;

	private static final Logger log = LogManager.getLogger(MySqlGenderDao.class);

	private MySqlGenderDao() {
		// nothing
	}

	public static synchronized GenderDao getInstance() {
		if (instance == null) {
			instance = new MySqlGenderDao();
		}
		return instance;
	}

	@Override
	public List<Gender> getAllGenders() throws SQLException {
		List<Gender> genders = new ArrayList<>();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM gender");
				ResultSet rs = st.executeQuery();) {
			while (rs.next()) {
				Gender gender = new Gender();
				gender.setId(rs.getInt(1));
				gender.setName(rs.getString(2));
				genders.add(gender);
			}
		} catch (SQLException e) {
			log.error("Can not get genders from database");
			throw e;
		}
		return genders;
	}

	@Override
	public Gender getGenderById(int id) throws SQLException {
		Gender gender = new Gender();
		try(Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM gender WHERE id=?")){
			st.setInt(1, id);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					int k = 1;
					gender.setId(rs.getInt(k++));
					gender.setName(rs.getString(k));
				}
			}
		}
		return gender;
	}

}
