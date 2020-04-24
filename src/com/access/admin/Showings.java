package com.access.admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public abstract class Showings {
    public static int getRoomId(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idRoom FROM filmScreenings WHERE idScreenings="+id);
            if (rs.next()) {
                return rs.getInt("idRoom");
            } else return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static int getMovieId(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idmovie FROM filmScreenings WHERE idScreenings="+id);
            if (rs.next()) {
                return rs.getInt("idmovie");
            } else return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static Calendar getDate(int id){
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT start FROM filmScreenings WHERE idScreenings="+id);
            Timestamp time;
            Calendar calendar = Calendar.getInstance();
            if (rs.next()) {
                time = rs.getTimestamp("start");
                calendar.setTimeInMillis(time.getTime());
            }
            stmt.close();

            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ArrayList<Calendar> getDates(){
        ArrayList <Calendar> dates = new ArrayList<>();
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT start FROM filmScreenings");
            Timestamp time;
            Calendar calendar = Calendar.getInstance();
            while (rs.next()) {
                time = rs.getTimestamp("start");
                calendar.setTimeInMillis(time.getTime());
                dates.add(calendar);
            }
            stmt.close();
            return dates;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ArrayList <Integer> getAllShowings(int id) {
        ArrayList<Integer> x = new ArrayList<Integer>();
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idScreenings FROM filmScreenings");
            while (rs.next()) {
                x.add(rs.getInt("idScreenings"));
            }
            stmt.close();
            return x;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }

    public static int getNumberOfShowings(int id) {
        return getAllShowings(id).size();
    }

    public static ArrayList <Integer> getShowings(int idMovie, int idRoom) {
        ArrayList<Integer> showings = new ArrayList<Integer>();
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idScreenings FROM filmScreenings WHERE idmovie='"+idMovie+"'AND idRoom='"+idRoom+"'");
            while (rs.next()) {
                showings.add(rs.getInt("idScreenings"));
            }
            stmt.close();
            return showings;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showings;
    }


    public static void addShowing(int idMovie, int idRoom, String format,
                                  Timestamp start, Timestamp end){
        try {
            Connection conn = DBConn.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO filmScreenings " +
                    "(idmovie, idRoom, format, start, end) VALUES (?,?,?,?,?)");

            stmt.setInt(1,idMovie);
            stmt.setInt(2,idRoom);
            stmt.setString(3,format);
            stmt.setTimestamp(4,start);
            stmt.setTimestamp(5,end);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteShowing(int idShow){
        try {
            Connection conn = DBConn.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM filmScreenings WHERE idScreenings=" + idShow);
            stmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void deleteShowingByMovie(int idMovie){
        try {
            Connection conn = DBConn.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM filmScreenings WHERE idmovie=" + idMovie);
            stmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void deleteShowingByRoom(int idRoom){
        try {
            Connection conn = DBConn.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM filmScreenings WHERE idRoom=" + idRoom);
            stmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    public static void editShowing()
}
