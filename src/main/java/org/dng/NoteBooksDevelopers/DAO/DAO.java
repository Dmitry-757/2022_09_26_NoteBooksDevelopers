package org.dng.NoteBooksDevelopers.DAO;

import org.dng.NoteBooksDevelopers.Model.NotebookDeveloper;

import java.sql.*;
import java.util.*;

public class DAO {

    public static boolean isExistDevById(long id) {
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

    public static NotebookDeveloper getDeveloperById(long id) {
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


    public static  List<NotebookDeveloper> getAllDev() {

        String sql_query = "Select * from notebookdev_db.notebookdev_tbl;";
        List < NotebookDeveloper > units = new LinkedList<>();

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



    public static Map<Integer, String> getDevHistoryById(long id) {
        Map<Integer, String> historyMap = new HashMap<>();
        String sql_query = "select * from notebookdev_db.devhistory_tbl where devId = "+id+";";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(sql_query);
            while (resultSet.next()) {
                int recId = resultSet.getInt("id");
                String devHistory = resultSet.getString("devhistory");
                historyMap.put(recId,devHistory);
            }
            return historyMap;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<Integer, String> getDevModelsById(long id) {
        Map<Integer, String> historyMap = new HashMap<>();
        String sql_query = "select * from notebookdev_db.models_tbl where devId = "+id+";";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(sql_query);
            while (resultSet.next()) {
                int recId = resultSet.getInt("id");
                String modelName = resultSet.getString("modelName");
                historyMap.put(recId,modelName);
            }
            return historyMap;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<byte[]> getPhotosById(String sql_query) {
        List<byte[]> photoList = new LinkedList<>();
//        String sql_query = "select * from notebookdev_db.news_photo_tbl where newsId = "+id+";";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(sql_query);
            while (resultSet.next()) {
                byte[] photo = resultSet.getBytes("photo");
                photoList.add(photo);
            }
            return photoList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<byte[]> getNewsPhotosById(long id) {
        String sql_query = "select * from notebookdev_db.news_photo_tbl where newsId = "+id+";";
        return getPhotosById(sql_query);
    }
    public static List<byte[]> getShortNewsPhotosById(long id) {
        String sql_query = "select * from notebookdev_db.shortnews_tbl where id = "+id+";";
        return getPhotosById(sql_query);
    }

    public static List<byte[]> getHistoryPhotosByDevHistoryId(long id) {
//        List<byte[]> historyPhotoList = new LinkedList<>();
        String sql_query = "select * from notebookdev_db.devhistory_photo_tbl where devhistoryId = "+id+";";
//        try (Connection connection = DBConnection.getConnection();
//             Statement statement = connection.createStatement()
//        ) {
//            ResultSet resultSet = statement.executeQuery(sql_query);
//            while (resultSet.next()) {
//                byte[] photo = resultSet.getBytes("photo");
//                historyPhotoList.add(photo);
//            }
//            return historyPhotoList;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
        return getPhotosById(sql_query);
    }

    public static List<byte[]> getModelsPhotoById(long id) {
        String sql_query = "select * from notebookdev_db.models_tbl where id = "+id+";";
        return getPhotosById(sql_query);
    }



    public static Map<Integer, String> getNews() {
        Map<Integer, String> newsMap = new HashMap<>();
        String sql_query = "select * from notebookdev_db.shortnews_tbl;";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(sql_query);
            while (resultSet.next()) {
                int recId = resultSet.getInt("id");
                String newsRec = resultSet.getString("shortNews");
                newsMap.put(recId,newsRec);
            }
            return newsMap;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<Integer, String> getDetailedNewsByShortNewsId(long id) {
        Map<Integer, String> newsMap = new HashMap<>();
        String sql_query = "select * from notebookdev_db.detailed_news_tbl where shortNewsId = "+id+";";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(sql_query);
            while (resultSet.next()) {
                int recId = resultSet.getInt("id");
                String newsRec = resultSet.getString("news");
                newsMap.put(recId,newsRec);
            }
            return newsMap;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<byte[]> getDetailedNewsPhotosByDetailedNewsId(long id) {
        String sql_query = "select * from notebookdev_db.detailednews_photo_tbl where detailedNewsId = "+id+";";
        return getPhotosById(sql_query);
    }


    //ищем детализированные описания которые относятся к конкретной модели (where modelsId = id)
    public static Map<Integer, String> getDetailedModelsByModelId(long id) {
        Map<Integer, String> newsMap = new HashMap<>();
        String sql_query = "select * from notebookdev_db.models_detailed_tbl where modelsId = "+id+";";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(sql_query);
            while (resultSet.next()) {
                int recId = resultSet.getInt("id");
                String description = resultSet.getString("description");
                newsMap.put(recId, description);
            }
            return newsMap;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<byte[]> getDetailedModelsPhotosByDetailedModelsId(long id) {
        String sql_query = "select * from notebookdev_db.models_detailed_photo_tbl where detailedModelId = "+id+";";
        return getPhotosById(sql_query);
    }



}
