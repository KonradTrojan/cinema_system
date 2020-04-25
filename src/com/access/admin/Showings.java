package com.access.admin;

import org.omg.PortableInterceptor.INACTIVE;

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
    public static String getTime(int idShow) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT start FROM filmScreenings WHERE idScreenings="+idShow);
            Timestamp timestamp = null;
            if (rs.next()) {
                timestamp = rs.getTimestamp("start");
            }
            Calendar cal = null;
            cal.setTimeInMillis(timestamp.getTime());
            String time = cal.get(Calendar.HOUR)+"."+cal.get(Calendar.MINUTE);
            return time;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static Timestamp getDate(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT start FROM filmScreenings WHERE idScreenings="+id);
            if (rs.next())
                return rs.getTimestamp("start");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static Timestamp getDateEnd(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT end FROM filmScreenings WHERE idScreenings="+id);
            if (rs.next())
                return rs.getTimestamp("end");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Integer> getShowingsSameDay(int idMovie,int idRoom, Timestamp date){
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM filmScreenings WHERE idRoom="+idRoom+" AND idmovie="+idMovie);
            ArrayList<Integer> times = new ArrayList<>();
            Timestamp timestamp;
            Calendar cal1 = null;
            cal1.setTimeInMillis(date.getTime());
            while (rs.next()) {
                timestamp = rs.getTimestamp("start");
                Calendar cal2 = null;
                cal2.setTimeInMillis(timestamp.getTime());
                if(cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR))
                    if(cal1.get(Calendar.MONTH)==cal2.get(Calendar.MONTH))
                        if(cal1.get(Calendar.DAY_OF_MONTH)==cal2.get(Calendar.DAY_OF_MONTH))
                            times.add(rs.getInt("idScreenings"));
            }
            stmt.close();
            return times;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getDateString(int id){
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT start FROM filmScreenings WHERE idScreenings="+id);
            Timestamp time;
            Calendar calendar = Calendar.getInstance();
            String date = null;
            if (rs.next()) {
                time = rs.getTimestamp("start");
                calendar.setTimeInMillis(time.getTime());
                calendar.set(Calendar.MONTH,1);
                date = calendar.get(Calendar.DAY_OF_MONTH)+"."+calendar.get(Calendar.MONTH)+"."+calendar.get(Calendar.YEAR);
            }
            stmt.close();
            return date;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Calendar> getDates(int idMovie, int idRoom, int idshowing){
        ArrayList <Calendar> dates = new ArrayList<>();
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT start FROM filmScreenings");
            Timestamp time;

            while (rs.next()) {
                Calendar calendar = Calendar.getInstance();
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
    public static ArrayList <Integer> getAllShowings() {
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

    public static int getNumberOfShowings() {
        return getAllShowings().size();
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

    public static ArrayList<Integer> getShowingsByIdRoom(int idRoom){
        ArrayList<Integer> showings = new ArrayList<>();
        try {
            Connection conn = DBConn.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idScreenings FROM filmScreenings WHERE idRoom= "+idRoom);
            while (rs.next()){
                showings.add(rs.getInt("idScreenings"));
            }
            return showings;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static boolean roomIsFree(int idRoom,Timestamp start, Timestamp end){
        ArrayList<Integer> showings = getShowingsByIdRoom(idRoom);
        if(showings.isEmpty()){
            return true;
        }else{
            for (Integer idShow : showings) {
                Timestamp showInRoomStart = Showings.getDate(idShow);
                Timestamp showInRoomEnd = Showings.getDateEnd(idShow);
                if ((start.getTime() > showInRoomEnd.getTime()) || (end.getTime() < showInRoomStart.getTime()) ||
                        (start.getTime() < showInRoomStart.getTime() && end.getTime() > showInRoomEnd.getTime()))
                    return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        int i = 0;
//        for(Calendar cal : Objects.requireNonNull(getDates())){
//            System.out.println(i + "wpis");
//            System.out.println(cal.get(Calendar.HOUR));
//            System.out.println(cal.get(Calendar.MINUTE));
//            System.out.println(cal.get(Calendar.DAY_OF_MONTH));
//
//            i++;
//        }
    }
}
