package com.taxi.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.dao.AccountDao;
import com.taxi.dao.CarDao;
import com.taxi.dao.DAOFactory;
import com.taxi.dao.GenderDao;
import com.taxi.dao.RoleDao;
import com.taxi.entity.Account;
import com.taxi.entity.Car;
import com.taxi.entity.Role;

public class MySqlAccountDao implements AccountDao {

	private static final String SELECT_ID_FROM_BY_NUMBER = "SELECT id FROM account WHERE login=?";

	private static final Logger log = LogManager.getLogger(MySqlAccountDao.class);

	private static AccountDao instance;
	private static RoleDao rdao = DAOFactory.getInstance().getRoleDao();
	private static GenderDao gdao = DAOFactory.getInstance().getGenderDao();
	private static CarDao cdao = DAOFactory.getInstance().getCarDao();

	public MySqlAccountDao() {
		// nothing
	}

	public static synchronized AccountDao getInstance() {
		if (instance == null) {
			instance = new MySqlAccountDao();
		}
		return instance;
	}

	@Override
	public int getIdByNumber(String number) throws SQLException {
		int id = 0;
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement(SELECT_ID_FROM_BY_NUMBER)) {
			st.setString(1, number);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					id = rs.getInt(1);
				}
			}
		} catch (SQLException ex) {
			log.error("Cant get id from DB");
			log.error(ex.getMessage());
			ex.printStackTrace();
			throw ex;
		}
		return id;
	}

	@Override
	public Account getAccountById(int id) throws SQLException {
		Account account = new Account();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM account WHERE id=?");) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					int k = 1;
					account.setId(rs.getInt(k++));
					account.setLogin(rs.getString(k++));
					account.setPassword(rs.getString(k++));
					account.setRole(instance.getRoleById(rs.getInt(k++)));
					account.setMail(rs.getString(k++));
					account.setName(rs.getString(k++));
					account.setSurname(rs.getString(k++));
				}
			}
		} catch (SQLException ex) {
			log.error("Cant get Account by id from DB");
			log.error(ex.getMessage());
			ex.printStackTrace();
			throw ex;
		}
		return account;
	}

	@Override
	public Role getRoleById(int id) throws SQLException {
		Role role = new Role();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM role WHERE id=?");) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					int k = 1;
					role.setId(rs.getInt(k++));
					role.setRole(rs.getString(k));
				}
			}
		} catch (SQLException ex) {
			log.error("Cant get Role from DB");
			log.error(ex.getMessage());
			ex.printStackTrace();
			throw ex;
		}
		return role;
	}

	@Override
	public void addUser(Account account) throws SQLException {

		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con
						.prepareStatement(
								"INSERT INTO account (id, login, password, role_id, mail, name, surname, gender_id)"
										+ "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)",
								PreparedStatement.RETURN_GENERATED_KEYS)) {
			int k = 1;
			st.setString(k++, account.getLogin());
			st.setString(k++, account.getPassword());
			st.setInt(k++, account.getRole().getId());
			st.setString(k++, account.getMail());
			st.setString(k++, account.getName());
			st.setString(k++, account.getSurname());
			st.setInt(k++, account.getGender().getId());
			if (st.executeUpdate() > 0) {
				try (ResultSet rs = st.getGeneratedKeys()) {
					if (rs.next()) {
						account.setId(rs.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			log.error("Can not add user to database");
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Account getAccountByLogin(String login) throws SQLException {
		Account account = new Account();
		try (Connection con = MySqlDAOFactory.getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM account WHERE login=?")) {
			st.setNString(1, login);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					int k = 1;
					account.setId(rs.getInt(k++));
					account.setLogin(rs.getString(k++));
					account.setPassword(rs.getString(k++));
					account.setRole(rdao.getRoleById(rs.getInt(k++)));
					account.setMail(rs.getString(k++));
					account.setName(rs.getString(k++));
					account.setSurname(rs.getString(k++));
					account.setGender(gdao.getGenderById(rs.getInt(k++)));
				}
			}
		} catch (SQLException e) {
			log.error("Account not found: {}", e.getMessage());
			throw e;
		}
		return account;
	}

	@Override
	public void createDriver(Car car, Account account) throws SQLException {
		Connection con = MySqlDAOFactory.getConnection();
		try {
			con.setAutoCommit(false);
			con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			createAccount(account, con);
			car.setAccount(account);
			cdao.createCar(car, con);
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

	private void createAccount(Account account, Connection con) throws SQLException {
		try (PreparedStatement st = con
				.prepareStatement("INSERT INTO account (id, login, password, role_id, mail, name, surname, gender_id)"
						+ "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
			int k = 1;
			st.setString(k++, account.getLogin());
			st.setString(k++, account.getPassword());
			st.setInt(k++, account.getRole().getId());
			st.setString(k++, account.getMail());
			st.setString(k++, account.getName());
			st.setString(k++, account.getSurname());
			st.setInt(k++, account.getGender().getId());
			if (st.executeUpdate() > 0) {
				try (ResultSet rs = st.getGeneratedKeys()) {
					if (rs.next()) {
						log.debug("Resutl set: {} ", rs);
						account.setId(rs.getInt(1));
					}
				}
			}
			log.debug("Account: {} ", account);
		}
	}
}
