package org.dng.NoteBooksDevelopers.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MSSQLConnection {
    private static String username = "sa";
    private static String password = "a430LBcn";
//    private static String URL = "jdbc:jtds:sqlserver://localhost:1433/myStore";
    private static String URL = "jdbc:jtds:sqlserver://localhost:1433/myStore";
    public static Connection connectionExp;

    static {
//        try (Connection connection = DriverManager.getConnection(URL, username, password);)
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
