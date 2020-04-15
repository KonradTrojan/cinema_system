import java.sql.*;

class Main {
    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://remotemysql.com:3306/w5NtVBqSWa", "w5NtVBqSWa", "F6mKAri9L0");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from test1");
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + " " + rs.getString(4));
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}