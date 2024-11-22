 
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String connectionString = PropertyUtil.getPropertyString();
            connection = DriverManager.getConnection(connectionString);
        }
        return connection;
    }
}


package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
    private static final String PROPERTY_FILE = "database.properties";
    
    public static String getPropertyString() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTY_FILE)) {
            props.load(fis);
            
            String host = props.getProperty("db.host");
            String port = props.getProperty("db.port");
            String dbName = props.getProperty("db.name");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");
            
            return String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s",
                               host, port, dbName, username, password);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database properties", e);
        }
    }
}

package myexceptions;

public class PolicyNotFoundException extends Exception {
    public PolicyNotFoundException(String message) {
        super(message);
    }
}