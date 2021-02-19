package com.taxi.dao;

import java.sql.SQLException;
import java.util.List;

import com.taxi.entity.Role;

public interface RoleDao {

	List<Role> getAllAccountRole() throws SQLException;

	Role getRoleByName(String parameter)throws SQLException;

	Role getRoleById(int id) throws SQLException;

}
