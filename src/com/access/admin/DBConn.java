package com.access.admin;

import java.sql.*;

public abstract class DBConn {
    public DBConn() throws SQLException {
    }

    public static ResultSet execute(String query) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://remotemysql.com:3306/w5NtVBqSWa",
                    "w5NtVBqSWa",
                    "F6mKAri9L0");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            //con.close();

            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void update(String query) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://remotemysql.com:3306/w5NtVBqSWa",
                    "w5NtVBqSWa",
                    "F6mKAri9L0");

            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection con;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(
                    "jdbc:mysql://remotemysql.com:3306/w5NtVBqSWa",
                    "w5NtVBqSWa",
                    "F6mKAri9L0");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
    //wtf nie wiem o co chodzi ale dziala i jest duzo szybsze

    public static Connection getConnection() {
        return con;
    }
}