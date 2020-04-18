package com.access.admin;

import java.sql.ResultSet;

public abstract class Showings {
    int getRoomId(int id) {
        try {
            ResultSet rs = DBConn.execute("SELECT idRoom FROM filmScreenings WHERE idScreenings="+id);
            if (rs.next()) {
                return rs.getInt("RoomID");
            } else return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    int getMovieId(int id) {
        try {
            ResultSet rs = DBConn.execute("SELECT idmovie FROM filmScreenings WHERE idScreenings="+id);
            if (rs.next()) {
                return rs.getInt("RoomID");
            } else return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
