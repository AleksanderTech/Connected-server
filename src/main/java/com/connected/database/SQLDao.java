package com.connected.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLDao implements UserDao {

	private Connection connection;

	public SQLDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public User getUser(String nick, String password) throws Exception {
		Statement statement = (Statement) connection.createStatement();
		String selectQuery = "select nick,password from users where nick=\"" + nick + "\"and password=\"" + password
				+ "\";";
		ResultSet resultSet = statement.executeQuery(selectQuery);
		if (!resultSet.isBeforeFirst()) {
			return new User();
		} else {
			User user = null;
			resultSet.next();
			String nickDb = resultSet.getString("nick");
			String passwordDb = resultSet.getString("password");
			user = new User(nickDb, passwordDb);
			return user;
		}
	}

	@Override
	public boolean insertUser(User user) throws Exception {
		String password = user.getPassword();
		String nick = user.getNick();
		Statement statement = (Statement) connection.createStatement();
		String insertQuery = "insert into users values('" + nick + "','" + password + "');";
		String selectQuery = "select nick from users where nick=\"" + nick + "\";";
		ResultSet resultSet = statement.executeQuery(selectQuery);
		if (!resultSet.isBeforeFirst()) {
			statement.execute(insertQuery);
			return true;
		} else {
			return false;
		}
	}
}