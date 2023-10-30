package config;

import java.io.IOException;
import java.util.Properties;

public class LoadJDBCSettingsFromPropertiesFile {

    private final static Properties PROPERTIES = new Properties();


    static {
        loadProperties();
    }

    private LoadJDBCSettingsFromPropertiesFile(){


    }

    public static String get(String key){

        return PROPERTIES.getProperty(key);

    }
    private static void loadProperties(){

        try (var inputStream=LoadJDBCSettingsFromPropertiesFile.class.getClassLoader()
                .getResourceAsStream("JDBCSetting.properties")){

            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
