package com.access.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class Users {
    public static String getName(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM users WHERE idUser=" + id);
            if (rs.next()) {
                return rs.getString(2);
            } else return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static String getSurname(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT surname FROM users WHERE idUser=" + id);
            if (rs.next()) {
                return rs.getString(3);
            } else return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static String getEmail(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT email FROM users WHERE idUser=" + id);
            if (rs.next()) {
                return rs.getString(4);
            } else return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
