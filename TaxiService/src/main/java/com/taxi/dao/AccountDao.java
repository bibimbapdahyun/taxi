package com.taxi.dao;

import java.sql.SQLException;

import com.taxi.entity.Account;
import com.taxi.entity.Car;
import com.taxi.entity.Role;

public interface AccountDao {
	Role getRoleById(int id) throws SQLException;
	int getIdByNumber(String number) throws SQLException;
	Account getAccountById(int id) throws SQLException;
	void addUser(Account account) throws SQLException;
	Account getAccountByLogin(String login) throws SQLException;
	void createDriver(Car car, Account account) throws SQLException;
}
