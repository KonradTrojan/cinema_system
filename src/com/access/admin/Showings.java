package com.access.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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


}
