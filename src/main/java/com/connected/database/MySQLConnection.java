package com.connected.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySQLConnection {

    private Connection connection;

    public MySQLConnection(String port, String database, String user, String password) throws SQLException, ClassNotFoundException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/" + database + "?useSSL=false&useUnicode=true&serverTimezone=UTC",
                user, password);
    }

    public Connection getConnection() {
        return connection;
    }

}