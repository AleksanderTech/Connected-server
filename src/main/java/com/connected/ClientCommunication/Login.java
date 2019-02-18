package com.connected.ClientCommunication;

import com.connected.database.User;
import com.connected.database.UserDao;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Login {

	private Socket socket;
	private List<Socket> connections;
	private List<User> users;
	private UserDao userDao;

	public Login(Socket socket, List<Socket> connections, List<User> users, UserDao userDao) throws IOException {
		this.socket = socket;
		this.connections = connections;
		this.users = users;
		this.userDao = userDao;
	}

	public User logIn(String nick, String password) throws Exception {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		password=hash(password, "MD5");
		User user = userDao.getUser(nick, password);
		boolean login = false;
		if (user.getNick() != null && user.getPassword() != null) {
			if (!users.toString().contains(user.getNick())) {
				users.add(user);
				connections.add(socket);
				login = true;
			}
		}
		printWriter.println(login);
		printWriter.flush();
		return user;
	}

	public void createAccount(String nick, String password) throws Exception {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		password=hash(password, "MD5");
		boolean account = userDao.insertUser(new User(nick, password));
		printWriter.println(account);
		printWriter.flush();
	}

	public String hash(String password, String algorithm) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		return new String(md.digest(password.getBytes()));
	}
}
