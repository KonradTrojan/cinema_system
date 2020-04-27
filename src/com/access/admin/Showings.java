package com.access.admin;

import java.sql.*;
import java.util.ArrayList;

public abstract class Showings {
    public static int getRoomId(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idRoom FROM filmScreenings WHERE idScreenings=" + id);
            if (rs.next()) {
                return rs.getInt("idRoom");
            } else return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getMovieId(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idmovie FROM filmScreenings WHERE idScreenings=" + id);
            if (rs.next()) {
                return rs.getInt("idmovie");
            } else return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Timestamp getDateStart(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT start FROM filmScreenings WHERE idScreenings=" + id);
            if (rs.next())
                return rs.getTimestamp("start");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Timestamp getDateEnd(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT end FROM filmScreenings WHERE idScreenings=" + id);
            if (rs.next())
                return rs.getTimestamp("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Integer> getShowingsSameDay(int idMovie, int idRoom, String date) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM filmScreenings WHERE idRoom=" + idRoom + " AND idmovie=" + idMovie);
            ArrayList<Integer> times = new ArrayList<>();
            Timestamp timestamp;

            while (rs.next()) {
                timestamp = rs.getTimestamp("start");
                if (date.substring(0, 10).equals(timestamp.toString().substring(0, 10)))
                    times.add(rs.getInt("idScreenings"));
            }
            stmt.close();
            return times;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Integer> getAllShowings() {
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

    public static ArrayList<Integer> getAllShowingsByMovie(int idMovie) {
        ArrayList<Integer> showings = new ArrayList<Integer>();
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idScreenings FROM filmScreenings WHERE idmovie=" + idMovie);
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

    public static ArrayList<Integer> getShowingsByMovieAndRoom(int idMovie, int idRoom) {
        ArrayList<Integer> showings = new ArrayList<Integer>();
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idScreenings FROM filmScreenings WHERE idmovie='" + idMovie + "'AND idRoom='" + idRoom + "'");
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
                                  Timestamp start, Timestamp end) {
        try {
            Connection conn = DBConn.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO filmScreenings " +
                    "(idmovie, idRoom, format, start, end) VALUES (?,?,?,?,?)");

            stmt.setInt(1, idMovie);
            stmt.setInt(2, idRoom);
            stmt.setString(3, format);
            stmt.setTimestamp(4, start);
            stmt.setTimestamp(5, end);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteShowing(int idShow) {
        try {
            Connection conn = DBConn.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM filmScreenings WHERE idScreenings=" + idShow);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteShowingByMovie(int idMovie) {
        try {
            Connection conn = DBConn.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM filmScreenings WHERE idmovie=" + idMovie);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteShowingByRoom(int idRoom) {
        try {
            Connection conn = DBConn.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM filmScreenings WHERE idRoom=" + idRoom);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> getShowingsByIdRoom(int idRoom) {
        ArrayList<Integer> showings = new ArrayList<>();
        try {
            Connection conn = DBConn.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idScreenings FROM filmScreenings WHERE idRoom= " + idRoom);
            while (rs.next()) {
                showings.add(rs.getInt("idScreenings"));
            }
            return showings;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static int getShowingByIdRoom(int idRoom) {
        try {
            Connection conn = DBConn.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idScreenings FROM filmScreenings WHERE idRoom= " + idRoom);
            if (rs.next()) {
                return rs.getInt("idScreenings");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }


    public static boolean roomIsFree(int idRoom, Timestamp start, Timestamp end) {
        ArrayList<Integer> showings = getShowingsByIdRoom(idRoom);
        if (showings.isEmpty()) {
            return true;
        } else {
            for (Integer idShow : showings) {
                Timestamp showInRoomStart = Showings.getDateStart(idShow);
                Timestamp showInRoomEnd = Showings.getDateEnd(idShow);
                if (!((start.getTime() > showInRoomEnd.getTime()) || (end.getTime() < showInRoomStart.getTime())))
                    return false;
                else if ((start.getTime() < showInRoomStart.getTime() && end.getTime() > showInRoomStart.getTime()))
                    return false;
                else if ((start.getTime() < showInRoomEnd.getTime() && end.getTime() > showInRoomEnd.getTime()))
                    return false;
            }
            return true;
        }
    }

    public static void editRoomId(int oldRoomId, int newRoomId) {
        try {
            Connection conn = DBConn.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE filmScreenings SET idRoom=? WHERE idRoom=" + oldRoomId);
            stmt.setInt(1, newRoomId);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
