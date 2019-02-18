package com.connected.database;


public interface UserDao {

	User getUser(String nick, String password) throws Exception;

	boolean insertUser(User user) throws Exception;

}
