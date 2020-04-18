package com.access.admin;

import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class Rooms {
    public static int getNumOfSeats(int id) {
        try {
            ResultSet rs = DBConn.execute("SELECT numOfSeats FROM rooms WHERE idRooms=" + id);
            if (rs.next()) {
                return rs.getInt(2);
            } else return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static int getNumOfRows(int id) {
        try {
            ResultSet rs = DBConn.execute("SELECT numOfRows FROM rooms WHERE idRooms=" + id);
            if (rs.next()) {
                return rs.getInt(3);
            } else return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


}
