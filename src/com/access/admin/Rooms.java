package com.access.admin;

import java.sql.*;
import java.util.ArrayList;

public abstract class Rooms {
    public static int getNumOfSeats(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT numOfSeats FROM rooms WHERE idRoom=" + id);
            int numOfSeats = 0;
            if (rs.next()) {
                numOfSeats = rs.getInt("numOfSeats");
            }
            stmt.close();
            return numOfSeats;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getNumOfRows(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT numOfRows FROM rooms WHERE idRoom=" + id);
            int numOfRows = 0;
            if (rs.next()) {
                numOfRows = rs.getInt("numOfRows");
            }
            stmt.close();
            return numOfRows;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static ArrayList<Integer> getAllRooms() {
        ArrayList<Integer> x = new ArrayList<Integer>();
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idRoom FROM rooms");
            while (rs.next()) {
                x.add(rs.getInt("idRoom"));
            }
            stmt.close();
            return x;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }

    public static void deleteRoom(int idRoom){
        Connection connection = DBConn.getConnection();
        PreparedStatement statement = null;
        String sql_ = "DELETE FROM rooms WHERE idRoom =? ";

        try {
            statement = connection.prepareStatement(sql_);
            statement.setInt(1,idRoom);

            statement.executeUpdate();

        }catch (SQLException ek) {
            System.out.println("SQLException: - " + ek);
        } finally {
            try {
                statement.close();
            } catch (SQLException el) {
                System.out.println("SQLException Finally: - " + el);
            }
        }

    }




}
