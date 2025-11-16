package rohita.example.bloodbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/bloodbank?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "Rohita@2006";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
