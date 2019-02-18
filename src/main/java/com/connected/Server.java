package com.connected;

import com.connected.ClientCommunication.LinkedNode;
import com.connected.database.MySQLConnection;
import com.connected.database.SQLDao;
import com.connected.database.User;
import com.connected.database.UserDao;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy, hh:mm");
    private List<User> users;
    private List<Socket> connections;
    private UserDao userDao;
    private Executor executor;

    public Server(UserDao userDao, Executor executor) {
        this.userDao = userDao;
        this.executor = executor;
        this.connections = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        if (args.length == 0) {
            System.out.println("Executing with no parameters");
            InputStream input = Server.class.getResourceAsStream("/application.properties");
            properties.load(input);
        } else {
            System.out.println("Executing with parameters");
            InputStream input = new FileInputStream(args[0]);
            properties.load(input);
        }
        String port = properties.getProperty("sqlPort");
        String user = properties.getProperty("user");
        String database = properties.getProperty("database");
        String password = properties.getProperty("password");
        String serverPort = properties.getProperty("serverPort");
        MySQLConnection connection = new MySQLConnection(port, database, user, password);
        SQLDao sqlDao = new SQLDao(connection.getConnection());
        Server server = new Server(sqlDao, Executors.newCachedThreadPool());
        server.start(serverPort);
    }

    public void start(String registryPort) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(registryPort))) {
            while (true) {
                System.out.println("Waiting for clients...");
                Socket socket = serverSocket.accept();
                System.out.println(
                        String.format("Client accepted...  %s", DATE_FORMAT.format(System.currentTimeMillis())));
                executor.execute(new LinkedNode(socket, connections, users, userDao));
            }
        }
    }
}
