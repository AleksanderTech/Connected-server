package com.connected.ClientCommunication;

import com.connected.database.User;

public class Interpreter {

	private Distribution distribution;
	private Login login;
	private User user;

	public Interpreter(Distribution distribution, Login login) {
		this.distribution = distribution;
		this.login = login;
	}

	public String extractAction(String request) {
		String[] split = request.split("#&#");
		return split[0];
	}

	public String extractNick(String request) {
		String[] split = request.split("#&#");
		return split[1];
	}

	public String extractMessage(String request) {
		String[] split = request.split("#&#");
		return split[2];
	}

	public int getOrder(String action) {
		if (action.equals("sendMessage"))
			return 1;
		else if (action.equals("logIn"))
			return 2;
		else if (action.equals("createAccount"))
			return 3;
		else if (action.equals("exit"))
			return 4;
		else
			return 5;
	}

	public void followTheOrder(int order, String request) throws Exception {
		switch (order) {
			case 1: {
				String nick = extractNick(request);
				String message = extractMessage(request);
				distribution.sendMessage(nick, message);
				break;
			}
			case 2: {
				String nick = extractNick(request);
				String message = extractMessage(request);
				this.user = login.logIn(nick, message);
				distribution.sendOnlineUsers();
				break;
			}
			case 3: {
				String nick = extractNick(request);
				String message = extractMessage(request);
				login.createAccount(nick, message);
				break;
			}
			case 4: {
				distribution.removeUser(this.user);
				distribution.sendOnlineUsers();
				break;
			}
		}
	}
}
