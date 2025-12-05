package Assignment.Model;

import java.sql.*;

public class DBTest {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/techgadget_db";
        String user = "root";
        String password = "";

        return DriverManager.getConnection(url, user, password);
            
    }
}
