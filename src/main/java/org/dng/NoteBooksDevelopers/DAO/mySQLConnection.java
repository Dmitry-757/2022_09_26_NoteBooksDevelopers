package org.dng.NoteBooksDevelopers.DAO;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mySQLConnection {
    private static String username = "root";
    private static String password = "dingo1975";
//    private static String URL = "jdbc:mysql://localhost:3306/notebookdev_db";
    private static String URL = "jdbc:mysql://localhost:3306/notebookdev_db";
    public static Connection connectionExp;


    static {
//        try {
//            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
//        } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
//                 IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch ( ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try
        {
            Connection connection = DriverManager.getConnection(URL, username, password);
            if (connection != null) System.out.println("Connection Successful !\n");
            if (connection == null) System.exit(0);

            connectionExp = connection;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        //if connection is closed - lets open it again
        try {
            if (connectionExp.isClosed() ) {
                connectionExp = DriverManager.getConnection(URL, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionExp;
    }
}
