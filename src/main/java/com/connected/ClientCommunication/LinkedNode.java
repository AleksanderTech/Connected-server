package com.connected.ClientCommunication;

import com.connected.database.User;
import com.connected.database.UserDao;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class LinkedNode implements Runnable {

	private List<Socket> connections;
	private List<User> users;
	private Socket socket;
	private UserDao userDao;

	public LinkedNode(Socket socket, List<Socket> connections, List<User> users, UserDao userDao) {
		this.socket = socket;
		this.connections = connections;
		this.users = users;
		this.userDao = userDao;
	}

	@Override
	public void run() {
		try (Scanner scanner = new Scanner(socket.getInputStream())) {
			Distribution distribution = new Distribution(connections, users, socket);
			Login login = new Login(socket, connections, users, userDao);
			Interpreter interpreter = new Interpreter(distribution, login);
			boolean lifeCycle = true;
			distribution.sendOnlineUsers();

			while (lifeCycle) {
				if (!scanner.hasNextLine()) {
					lifeCycle = false;
				} else if (scanner.hasNextLine()) {
					String request = scanner.nextLine();
					String action = interpreter.extractAction(request);
					int order = interpreter.getOrder(action);
					interpreter.followTheOrder(order, request);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println("Thread is dying.");
	}
}