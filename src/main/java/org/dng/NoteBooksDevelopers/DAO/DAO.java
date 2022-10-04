package org.dng.NoteBooksDevelopers.DAO;

import org.dng.NoteBooksDevelopers.Model.NotebookDeveloper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    public static boolean isExistById(long id) {
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            String sql_query = "Select top 1 * from notebookdev_db.notebookdev_tbl where id = " + id;
            ResultSet resultSet = statement.executeQuery(sql_query);
            if (resultSet.next()) { //if in result of query exist at list one element
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static NotebookDeveloper getById(long id) {
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            //String sql_query = "Select top 1 * from notebookdev_tbl where id = " + id;
            String sql_query = "select * from notebookdev_db.notebookdev_tbl where id = "+id+" limit 1;";
            ResultSet resultSet = statement.executeQuery(sql_query);
            if (resultSet.next()) {
                int resId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                int employeesNumber = resultSet.getInt("employeesNumber");
                String logo = resultSet.getString("logo");
                String shortInfo = resultSet.getString("shortInfo");
                byte[] photo = resultSet.getBytes("photo");

                return new NotebookDeveloper(resId, name, country, logo, employeesNumber, shortInfo, photo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static  List<NotebookDeveloper> getAll() {

        String sql_query = "Select * from notebookdev_db.notebookdev_tbl;";
        List < NotebookDeveloper > units = new ArrayList< >();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int resId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                int employeesNumber = resultSet.getInt("employeesNumber");
                String logo = resultSet.getString("logo");
                String shortInfo = resultSet.getString("shortInfo");
                byte[] photo = resultSet.getBytes("photo");

                units.add(new NotebookDeveloper(resId, name, country, logo, employeesNumber, shortInfo, photo));

            }
        } catch (SQLException e) {
//            printSQLException(e);
            e.printStackTrace();
        }
        return units;
    }




}
