package com.access.admin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

public abstract class Movies {
    public static String getTitle(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT title FROM movies WHERE idMovie=" + id);
            String title = null;
            if (rs.next()) {
                title = rs.getString("title");
            }
            stmt.close();
            return title;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDirector(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT director FROM movies WHERE idMovie=" + id);
            String director = null;
            if (rs.next()) {
                director = rs.getString("director");
            }
            stmt.close();
            return director;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getId(String title) {

        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM movies WHERE title='" + title + "'");
            int id = 0;
            if (rs.next()) {
                id = rs.getInt("idMovie");
            }
            stmt.close();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static String getDescription(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT description FROM movies WHERE idMovie=" + id);
            String description = null;
            if (rs.next()) {
                description = rs.getString("description");
            }
            stmt.close();
            return description;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage getPoster(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT poster FROM movies WHERE idMovie=" + id);
            BufferedImage poster = null;
            if (rs.next()) {
                poster = ImageIO.read(rs.getBinaryStream("poster"));
            }
            stmt.close();
            return poster;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getTitles() {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT title FROM movies");
            ArrayList<String> movies = new ArrayList<>();
            while (rs.next()) {
                movies.add(rs.getString("title"));
            }
            stmt.close();
            return movies;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStars(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT stars FROM movies WHERE idMovie=" + id);
            String description = null;
            if (rs.next()) {
                description = rs.getString("stars");
            }
            stmt.close();
            return description;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWriter(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT writer FROM movies WHERE idMovie=" + id);
            String description = null;
            if (rs.next()) {
                description = rs.getString("writer");
            }
            stmt.close();
            return description;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAgeCategory(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ageCategory FROM movies WHERE idMovie=" + id);
            String description = null;
            if (rs.next()) {
                description = rs.getString("ageCategory");
            }
            stmt.close();
            return description;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getLengthInt(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT length FROM movies WHERE idMovie=" + id);
            if (rs.next()) {
                return rs.getInt("length");
            } else return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String getLength(int id) {
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT length FROM movies WHERE idMovie=" + id);
            if (rs.next()) {
                return rs.getString("length");
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Integer> getAllMovies() {
        ArrayList<Integer> x = new ArrayList<Integer>();
        try {
            Connection con = DBConn.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idMovie FROM movies");
            while (rs.next()) {
                x.add(rs.getInt("idMovie"));
            }
            stmt.close();
            return x;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x;
    }

    public static int getNumberOfMovies() {
        return getAllMovies().size();
    }

    public static void addMovie(String title, String description,
                                int length, String director, String writer,
                                String stars, String ageCategory, File poster) {

        Connection connection = null;
        PreparedStatement statement = null;
        FileInputStream inputStream = null;
        try {

            inputStream = new FileInputStream(poster);
            connection = DBConn.getConnection();

            statement = connection.prepareStatement("insert into movies (title, " +
                    "length," + "description,director,writer,ageCategory,stars,poster) " +
                    "values(?,?,?,?,?,?,?,?)");

            statement.setString(1, title);
            statement.setInt(2, length);
            statement.setString(3, description);
            statement.setString(4, director);
            statement.setString(5, writer);
            statement.setString(6, ageCategory);
            statement.setString(7, stars);
            statement.setBinaryStream(8, (InputStream) inputStream, (int) (poster.length()));

            statement.executeUpdate();

        } catch (FileNotFoundException ee) {
            System.out.println("FileNotFoundException: - " + ee);
        } catch (SQLException ek) {
            System.out.println("SQLException: - " + ek);
        } finally {
            try {
                statement.close();
            } catch (SQLException el) {
                System.out.println("SQLException Finally: - " + el);
            }
        }
    }

    public static void deleteMovie(String deletedTitle) {
        Connection connection = DBConn.getConnection();
        PreparedStatement statement = null;
        String sql_ = "DELETE FROM movies WHERE title =? ";

        try {
            Showings.deleteShowingByMovie(Movies.getId(deletedTitle));
            statement = connection.prepareStatement(sql_);
            statement.setString(1, deletedTitle);
            statement.executeUpdate();

        } catch (SQLException ek) {
            System.out.println("SQLException: - " + ek);
        } finally {
            try {
                statement.close();
            } catch (SQLException el) {
                System.out.println("SQLException Finally: - " + el);
            }
        }

    }

    // editMovie for edit with poster
    public static void editMovie(int idMovie, String title, String description,
                                 int length, String director, String writer,
                                 String stars, String ageCategory, File poster) {
        Connection connection = DBConn.getConnection();
        PreparedStatement statement = null;
        FileInputStream inputStream = null;

        String sql = "UPDATE movies set title=?, length=?,description=?,director=?," +
                "writer=?,ageCategory=?,stars=?,poster=? WHERE idMovie=" + idMovie;

        try {
            statement = connection.prepareStatement(sql);
            inputStream = new FileInputStream(poster);
            statement.setString(1, title);
            statement.setInt(2, length);
            statement.setString(3, description);
            statement.setString(4, director);
            statement.setString(5, writer);
            statement.setString(6, ageCategory);
            statement.setString(7, stars);
            statement.setBinaryStream(8, (InputStream) inputStream, (int) (poster.length()));
            statement.executeUpdate();
        } catch (FileNotFoundException ee) {
            System.out.println("FileNotFoundException: - " + ee);
        } catch (SQLException ek) {
            System.out.println("SQLException: - " + ek);
        } finally {
            try {
                statement.close();
            } catch (SQLException el) {
                System.out.println("SQLException Finally: - " + el);
            }
        }
    }

    // editMovie for edit without poster
    public static void editMovie(int idMovie, String title, String description,
                                 int length, String director, String writer,
                                 String stars, String ageCategory) {

        Connection connection = DBConn.getConnection();
        PreparedStatement statement = null;
        FileInputStream inputStream = null;

        String sql_ = "UPDATE movies set title=?, length=?,description=?,director=?," +
                "writer=?,ageCategory=?,stars=? WHERE idMovie=" + idMovie;
        try {
            statement = connection.prepareStatement(sql_);
            statement.setString(1, title);
            statement.setInt(2, length);
            statement.setString(3, description);
            statement.setString(4, director);
            statement.setString(5, writer);
            statement.setString(6, ageCategory);
            statement.setString(7, stars);
            statement.executeUpdate();
        } catch (SQLException ek) {
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
