package com.access.admin;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public abstract class DBConn {
    public static Connection con;

    static {
        Properties databaseProps = new Properties();
        try {
            databaseProps.load(new FileInputStream("database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String appVersion = databaseProps.getProperty("hostname");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(
                    "jdbc:mysql://" + databaseProps.getProperty("hostname") + ":3306/" + databaseProps.getProperty("database"),
                    databaseProps.getProperty("username"),
                    databaseProps.getProperty("password"));
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return con;
    }

}