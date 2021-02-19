package com.taxi.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.taxi.dao.RoleDao;
import com.taxi.entity.CarState;
import com.taxi.entity.Role;

public class MySqlRoleDao implements RoleDao {

	private static RoleDao instance;

	private MySqlRoleDao() {
		// nothing
	}

	public static synchronized RoleDao getInstance() {
		if (instance == null) {
			instance = new MySqlRoleDao();
		}
		return instance;
	}

	@Override
	public List<Role> getAllAccountRole() throws SQLException {
		List<Role> list = new ArrayList<>();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM role");
				ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				Role role = new Role();
				int k = 1;
				role.setId(rs.getInt(k++));
				role.setRole(rs.getString(k++));
				list.add(role);
			}
		}
		return list;
	}

	@Override
	public Role getRoleByName(String name) throws SQLException {
		Role role = new Role();
		try(Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT id FROM role WHERE user_role=?")){
			st.setString(1, name);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					role.setId(rs.getInt(1));
					role.setRole(name);
				}
			}
		}
		return role;
	}

	@Override
	public Role getRoleById(int id) throws SQLException {
		Role role = new Role();
		try(Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM role WHERE id=?")){
			st.setInt(1, id);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					int k = 1;
					role.setId(rs.getInt(k++));
					role.setRole(rs.getString(k));
				}
			}
		}
		return role;
	}
}
