package org.dng.NoteBooksDevelopers.DAO;

import org.dng.NoteBooksDevelopers.Model.NotebookDeveloper;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mySQLConnection {
    private static String username = "root";
    private static String password = "mypassword";


//    private static String URL = "jdbc:mysql://localhost:3306";
    private static String URL = "jdbc:mysql://sql-db";

    public static Connection connectionExp;


    static {
//        try {
//            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
//        } catch (InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
//                 IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
        try {
            //для подключения  mySQL версии выше 8.0 используем драйвер "com.mysql.cj.jdbc.Driver", а ниже "com.mysql.jdbc.Driver"
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            //Class.forName("com.mysql.jdbc.Driver");
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
            if (connectionExp.isClosed()||connectionExp==null) {
                connectionExp = DriverManager.getConnection(URL, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connectionExp;


    }
}
