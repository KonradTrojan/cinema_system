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

    public static void addShowing(int idMovie, int idRoom, String format,
                                  int day, Calendar start){
        try {
            Connection conn = DBConn.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO filmScreenings " +
                    "(idmovie, idRoom, format, day,start, end) VALUES (?,?,?,?,?,?)");

            stmt.setInt(1,idMovie);
            stmt.setInt(2,idRoom);
            stmt.setString(3,format);
            stmt.setInt(4,start.get(Calendar.DAY_OF_WEEK));
            stmt.setDate(5,new java.sql.Date(start.getTime().getTime()),start);

            start.add(Calendar.HOUR,Movies.getLengthInt(idMovie)/60);
            if (start.get(Calendar.MINUTE)+Movies.getLengthInt(idMovie)%60 > 60){
                start.add(Calendar.HOUR,1);
                start.add(Calendar.MINUTE,(start.get(Calendar.MINUTE)+Movies.getLengthInt(idMovie)%60)%60);
            }
            else
                start.add(Calendar.MINUTE,start.get(Calendar.MINUTE)+Movies.getLengthInt(idMovie));
            stmt.setDate(6,new java.sql.Date(start.getTime().getTime()),start);


            stmt.executeUpdate();
            stmt.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void deleteShowing(id )
}
