package com.access.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public abstract class Bookings {
    public static ArrayList<String> getBookingsToString(int idShowing){
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM bookings WHERE idShowing=" + idShowing);
            ArrayList<String> bookings = new ArrayList<>();
            String booking = null;
            while (rs.next()) {
                booking =  rs.getInt("idBooking") + ", Nr seansu: " +
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
}
