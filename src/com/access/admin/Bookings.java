package com.access.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

public abstract class Bookings {
    public static int getRow(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT row FROM bookings WHERE idBooking=" + id);
            int row = 0;
            if (rs.next()) {
                row = rs.getInt("row");
            }
            stmt.close();
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static int getSeat(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT seat FROM bookings WHERE idBooking=" + id);
            int seat = 0;
            if (rs.next()) {
                seat = rs.getInt("seat");
            }
            stmt.close();
            return seat;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void addBooking(int idShowing, int seat, int row) {
        try {
            Connection connection = DBConn.getConnection();
            
            PreparedStatement statement = connection.prepareStatement("INSERT INTO bookings (idShowing, idUser, row, seat) " +
                    "values(?,?,?,?)");

            statement.setInt(1, idShowing);
            statement.setInt(2, 0);
            statement.setInt(3, row);
            statement.setInt(4, seat);

            statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<String> getBookingsToString(int idShowing){
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM bookings WHERE idShowing=" + idShowing);
            ArrayList<String> bookings = new ArrayList<>();
            String booking;
            while (rs.next()) {
                booking =  rs.getInt("idBooking") + ",\t\t Nr seansu: " +
                rs.getInt("idShowing") +", Nr klienta: " + rs.getInt("idUser")+
                ", Sala: "+Showings.getRoomId(rs.getInt("idShowing")) + ", Rząd: " +
                rs.getInt("row") + ", Miejsce: " + rs.getInt("seat") +
                ", Dzień: ";

                bookings.add(booking);
            }
            stmt.close();
            return bookings;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void delete(int idBooking){
        try {
            Connection conn = DBConn.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM bookings WHERE idBooking=" +idBooking);
            stmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void deleteByIdShow(int idShow){
        try {
            Connection conn = DBConn.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM bookings WHERE idShowing=" +idShow);
            stmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> getAllBookings(int idShow) {
        ArrayList<Integer> x = new ArrayList<Integer>();
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idBooking FROM bookings WHERE idShowing="+idShow);
            while (rs.next()) {
                x.add(rs.getInt("idBooking"));
            }
            stmt.close();
            return x;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }
    public static int getNumberOfMovies(int idShow) {
        return getAllBookings(idShow).size();
    }


}
