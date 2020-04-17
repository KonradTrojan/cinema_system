package com.access.admin;

import java.sql.*;

public class DBConn {
    public static ResultSet execute (String query) {
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
}