package com.taxi.dao;

import com.taxi.dao.mysql.MySqlDAOFactory;

public abstract class DAOFactory {
	private static DAOFactory instance;

	public static synchronized DAOFactory getInstance() {
		if (instance == null) {
			instance = new MySqlDAOFactory();
		}
		return instance;
	}

	// Main entity DAO
	public abstract AccountDao getAccountDao();
	public abstract OrderDao getOrderDao();
	public abstract CarDao getCarDao();

	// Sub entity DAO
	public abstract CarStateDao getCarStateDao();
	public abstract CarTypeDao getTypeDao();
	public abstract RoleDao getRoleDao();
	public abstract TripStateDao getTripStateDao();
	public abstract GenderDao getGenderDao();
}
