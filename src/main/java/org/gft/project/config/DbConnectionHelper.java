package org.gft.project.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DbConnectionHelper {

    protected static String username = "sa";
    protected static String password = "";
    protected static String databaseUrl = "jdbc:h2:mem:h2Demo";
    private DbConnectionHelper() {
    }

    public static void setUsername(String username) {
        DbConnectionHelper.username = username;
    }

    public static void setPassword(String password) {
        DbConnectionHelper.password = password;
    }

    public static void setConnectionUrl(String connectionUrl) {
        DbConnectionHelper.databaseUrl = connectionUrl;
    }

    public static Connection createConnection() throws SQLException {

        Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        connectionProps.put("serverTimezone", "UTC");

        return DriverManager.getConnection(databaseUrl, connectionProps);

    }
}
