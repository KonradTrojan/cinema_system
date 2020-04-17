package com.access.admin;

import java.sql.*;
import java.util.ArrayList;

public abstract class Movies {


    public static String getTitle(int id) {
        try {
            ResultSet rs = DBConn.execute("SELECT title FROM movies WHERE idMovie=" + id);
            if (rs.next()) {
                return rs.getString("title");
            } else return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getTitles(){
        try {
            ResultSet rs = DBConn.execute("SELECT title FROM movies");
            ArrayList<String> movies = new ArrayList<>();
            while (rs.next()) {
                movies.add(rs.getString("title"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getLength(int id) {
        try {
            ResultSet rs = DBConn.execute("SELECT length FROM movies WHERE idMovie=" + id);
            if (rs.next()) {
                return rs.getString("length");
            } else return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }




    public static void main(String[] args) throws SQLException {
        System.out.println(Movies.getTitle(1));
    }

}
