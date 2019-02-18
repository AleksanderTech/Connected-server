package com.connected.ClientCommunication;

import com.connected.database.User;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Distribution {
	private Socket socket;
	private List<User> users;
	private List<Socket> connections;

	public Distribution(List<Socket> connections, List<User> users, Socket socket) {
		this.connections = connections;
		this.users = users;
		this.socket = socket;
	}

	public List<User> getUsers() {
		return users;
	}

	public void sendOnlineUsers() throws IOException {
		for (int i = 0; i < connections.size(); i++) {
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(connections.get(i).getOutputStream()));
			printWriter.println(users + "#&#" + "sendOnlineUsers");
			printWriter.flush();
		}
	}

	public void removeUser(User user) {

		for (int i = 0; i < connections.size(); i++) {
			if (connections.get(i) == socket) {
				connections.remove(i);
			}
		}

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(user)) {
				users.remove(i);
			}
		}
	}

	public void sendMessage(String nick, String message) {
		for (int i = 0; i < connections.size(); i++) {
			if (!(connections.get(i) == socket)) {
				PrintWriter printWriter = null;
				try {
					printWriter = new PrintWriter(new OutputStreamWriter(connections.get(i).getOutputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				printWriter.println(users + "#&#" + "sendMessage" + "#&#" + nick + "#&#" + message);
				printWriter.flush();
			}
		}
	}
}
