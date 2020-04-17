package com.access.admin;

import java.sql.ResultSet;

public class Users {
    public static String getName(int id) {
        try {
            ResultSet rs = DBConn.execute("SELECT name FROM users WHERE idUser=" + id);
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
            ResultSet rs = DBConn.execute("SELECT surname FROM users WHERE idUser=" + id);
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
            ResultSet rs = DBConn.execute("SELECT email FROM users WHERE idUser=" + id);
            if (rs.next()) {
                return rs.getString(4);
            } else return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
