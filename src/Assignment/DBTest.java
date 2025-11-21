package Assignment;

import java.sql.*;

public class DBTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/techgadget_db";
        String user = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected successfully!");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM member");

            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
