package com.taxi.dao.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.taxi.dao.AccountDao;
import com.taxi.dao.CarDao;
import com.taxi.dao.CarStateDao;
import com.taxi.dao.CarTypeDao;
import com.taxi.dao.DAOFactory;
import com.taxi.dao.GenderDao;
import com.taxi.dao.OrderDao;
import com.taxi.dao.RoleDao;
import com.taxi.dao.TripStateDao;

public class MySqlDAOFactory extends DAOFactory {
	private final OrderDao odao = MySqlOrderDao.getInstance();
	private final CarDao cdao = MySqlCarDao.getInstanse();
	private final AccountDao adao = MySqlAccountDao.getInstance();
	private final CarTypeDao tdao = MySqlCarTypeDao.getInstance();
	private final CarStateDao csdao = MySqlStateDao.getInstance();
	private final TripStateDao tsdao = MySqlTripStateDao.getInstance();
	private final RoleDao rdao = MySqlRoleDao.getInstance();
	private final GenderDao gdao = MySqlGenderDao.getInstance();

	private static final Logger log = LogManager.getLogger(MySqlDAOFactory.class);

	static Connection getConnection() throws SQLException {
		Connection con = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/mysql");
			con = ds.getConnection();
		} catch (NamingException ex) {
			log.error("Cannot obtain a connection from the pool", ex);
			throw new SQLException(ex);
		}
		return con;
	}

	public MySqlDAOFactory() {
		// nothing
	}

	@Override
	public OrderDao getOrderDao() {
		return odao;
	}

	@Override
	public CarDao getCarDao() {
		return cdao;
	}

	@Override
	public AccountDao getAccountDao() {
		return adao;
	}

	@Override
	public CarStateDao getCarStateDao() {
		return csdao;
	}

	@Override
	public CarTypeDao getTypeDao() {
		return tdao;
	}

	@Override
	public RoleDao getRoleDao() {
		return rdao;
	}

	@Override
	public TripStateDao getTripStateDao() {
		return tsdao;
	}

	@Override
	public GenderDao getGenderDao() {
		return gdao;
	}

}
