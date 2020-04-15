package com.access.admin;

import java.sql.*;

public abstract class Movies {
    Statement stmt;
    Connection con;

    private void openDataBase(){
        String url = "jdbc:mysql://remotemysql.com:3306/w5NtVBqSWa";
        String user = "w5NtVBqSWa";
        String password = "F6mKAri9L0";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.con = DriverManager.getConnection(
                    url, user, password);

            this.stmt = con.createStatement();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void closeDataBase() throws SQLException {
        con.close();
    }
}
