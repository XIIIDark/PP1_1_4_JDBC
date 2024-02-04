package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String HOST_NAME = "localhost";
    private static final String DB_NAME = "myschemas";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "uGV!hu3.24sdf";
    private static Connection conn = null;

    //    - при каждом обращении к getMySQLConnection() возвращай новый коннекшн
    public static Connection getMySQLConnection() {
        initMySQLConnection();
        return conn;
    }


    private static void initMySQLConnection() {

        String connectionURL = "jdbc:mysql://" + HOST_NAME + ":3306/" + DB_NAME;

        try {
            conn =  DriverManager.getConnection(connectionURL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
