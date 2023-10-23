package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionManager {

    private final static String USERNAME_KEY = "postgres";
    private final static String PASSWORD_KEY = "1";
    private final static String URL_KEY = "jdbc:postgresql://localhost:5432/postgres";
    private final static String DB_DRIVER_KEY = "db.driver.class";
    private static Connection CONNECTION;

    {
        loadDriver();
    }

    private ConnectionManager(){

    }

    public static Connection open(){

        try {
            return CONNECTION = DriverManager.getConnection(
                    LoadJDBCSettingsFromPropertiesFile.get(URL_KEY),
                    LoadJDBCSettingsFromPropertiesFile.get(USERNAME_KEY),
                    LoadJDBCSettingsFromPropertiesFile.get(PASSWORD_KEY)
            );
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public static void connectionClose(){

        try {
            if (CONNECTION!=null&&!CONNECTION.isClosed()){
                CONNECTION.isClosed();

            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver(){

        try {
            Class.forName(LoadJDBCSettingsFromPropertiesFile.get(DB_DRIVER_KEY));

        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}

