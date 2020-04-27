package com.access.admin;

import java.sql.*;

public abstract class DBConn {
    public static Connection con;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(
                    "jdbc:mysql://remotemysql.com:3306/w5NtVBqSWa",
                    "w5NtVBqSWa",
                    "F6mKAri9L0");
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return con;
    }

}