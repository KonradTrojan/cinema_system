import com.access.admin.DBConn;

import java.sql.ResultSet;

class Main {
    public static void main(String args[]) {
        ResultSet rs = DBConn.execute("select * from test1");
        try {
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + " " + rs.getString(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet rs2 = DBConn.execute("select * from test1");
        try {
            while (rs2.next())
                System.out.println(rs2.getInt(1) + "  " + rs2.getString(2) + "  " + rs2.getString(3) + " " + rs2.getString(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet rs3 = DBConn.execute("select * from test1");
        try {
            while (rs3.next())
                System.out.println(rs3.getInt(1) + "  " + rs3.getString(2) + "  " + rs3.getString(3) + " " + rs3.getString(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}