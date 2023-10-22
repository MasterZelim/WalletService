package connection;

import java.sql.*;


public class ConnectionDatabase {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "1";

    public static Connection connection() throws SQLException {

            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            return connection;

    }
}
