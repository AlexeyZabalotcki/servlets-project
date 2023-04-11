package ru.clevertec.zabalotcki.repository;

import ru.clevertec.zabalotcki.config.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        AppConfig config = AppConfig.getInstance();
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(config.getJdbcUrl(), config.getJdbcUser(), config.getJdbcPassword());
    }
}
