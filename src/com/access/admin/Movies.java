package com.access.admin;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;

public abstract class Movies {

    public static String getTitle(int id) {
        try {
            ResultSet rs = DBConn.execute("SELECT title FROM movies WHERE idMovie=" + id);
            if (rs.next()) {
                return rs.getString("title");
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDirector(int id) {
        try {
            ResultSet rs = DBConn.execute("SELECT director FROM movies WHERE idMovie=" + id);
            if (rs.next()) {
                return rs.getString("director");
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDescription(int id) {
        try {
            ResultSet rs = DBConn.execute("SELECT description FROM movies WHERE idMovie=" + id);
            if (rs.next()) {
                return rs.getString("description");
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPoster(int id) { //TODO
        try {
            ResultSet rs = DBConn.execute("SELECT poster FROM movies WHERE idMovie=" + id);
            if (rs.next()) {
                return rs.getString("poster");
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getTitles() {
        try {
            ResultSet rs = DBConn.execute("SELECT title FROM movies");
            ArrayList<String> movies = new ArrayList<>();
            while (rs.next()) {
                movies.add(rs.getString("title"));
            }
            return movies;
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList <Integer> getAllMovies() {
        ArrayList <Integer> x = new ArrayList<Integer>();
        try {
            ResultSet rs = DBConn.execute("SELECT idMovie FROM movies");
            if (rs.next()) {
                x.add(rs.getInt("idMovie"));
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }
    public static int getNumberOfMovies() {
        return getAllMovies().size();
    }

    public static void addMovie(String title, String description,
                                int length, String director, String writer,
                                String stars, String ageCategory, FileInputStream poster){

        DBConn.update("INSERT INTO movies (title,length,description,director, " +
                "writer, ageCategory,stars, poster) VALUES ('"+ title+"','"+length+"','"+
                description+"','"+director+"','"+writer+"','"+ageCategory+"','"+stars+"','"+poster+"');");
        // jescze trzeba zaimplementować dodawanie plakatu


    }


    public static void main(String[] args) throws SQLException {

        for (String title : getTitles()){
            System.out.println(title);
        }
        System.out.println(Movies.getTitles());
    }

}
