package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/java_project";
    private static final String userName = "root";
    private static final String password = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, userName, password);
            System.out.println("Connection OK");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DB_DRIVER);
                settings.put(Environment.URL, DB_URL);
                settings.put(Environment.USER, userName);
                settings.put(Environment.PASS, password);
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create");

                sessionFactory = new Configuration()
                        .addProperties(settings)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();

                System.out.println("Connection SUCCESS");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Connection ERROR");
            }
        }
        return sessionFactory;
    }
}
