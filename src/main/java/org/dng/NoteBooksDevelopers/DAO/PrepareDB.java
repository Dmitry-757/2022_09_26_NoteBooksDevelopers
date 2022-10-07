package org.dng.NoteBooksDevelopers.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class PrepareDB {
    public static void prepareBase() {
        String PREPARE_QUERY =
                "DROP DATABASE IF EXISTS notebookdev_db;"
                ;
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {

            statement.executeUpdate(PREPARE_QUERY);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        PREPARE_QUERY =
                        "CREATE DATABASE IF NOT EXISTS notebookdev_db;"
                ;
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {

            statement.executeUpdate(PREPARE_QUERY);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void createTables(){
        String CREATE_TABLE_notebookdev_tbl =
                "CREATE TABLE notebookdev_db.notebookdev_tbl (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  name VARCHAR(50) NOT NULL," +
                        "  country VARCHAR(45) NULL," +
                        "  employeesNumber INT NULL," +
                        "  shortInfo VARCHAR(300) NULL," +
                        "  logo VARCHAR(45) NULL," +
                        "  photo   MEDIUMBLOB null," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE);";


        String CREATE_TABLE_devHistory_tbl =
                "CREATE TABLE notebookdev_db.devhistory_tbl (\n" +
                        "  id INT NOT NULL AUTO_INCREMENT,\n" +
                        "  devId INT NOT NULL,\n" +
                        "  devhistory VARCHAR(500) NULL,\n" +
                        "  PRIMARY KEY (id),\n" +
                        "  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,\n" +
                        "  INDEX id_idx (devId ASC) VISIBLE,\n" +
                        "  CONSTRAINT devIdKey\n" +
                        "    FOREIGN KEY (devId)\n" +
                        "    REFERENCES notebookdev_db.notebookdev_tbl (id)\n" +
                        "    ON DELETE CASCADE\n" +
                        "    ON UPDATE CASCADE);";

        String CREATE_TABLE_devHistoryPhoto_tbl =
        "CREATE TABLE notebookdev_db.devhistory_photo_tbl (\n" +
                "  id INT NOT NULL AUTO_INCREMENT,\n" +
                "  photo MEDIUMBLOB NOT NULL,\n" +
                "  devhistoryId INT NOT NULL,\n" +
                "  PRIMARY KEY (id),\n" +
                "  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,\n" +
                "  INDEX historyKey_idx (devhistoryId ASC) VISIBLE,\n" +
                "  CONSTRAINT historyKey\n" +
                "    FOREIGN KEY (devhistoryId)\n" +
                "    REFERENCES notebookdev_db.devhistory_tbl (id)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE);";


        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.addBatch(CREATE_TABLE_notebookdev_tbl);
            statement.addBatch(CREATE_TABLE_devHistory_tbl);
            statement.addBatch(CREATE_TABLE_devHistoryPhoto_tbl);

            statement.executeBatch();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void fillNotebookdev(){
        String fillStr1 =
                "INSERT notebookdev_db.notebookdev_tbl(name, country, employeesNumber, shortInfo, logo, photo) \n" +
                        "VALUES (?, ?, ?, ?, ?, ?)";
//                        "VALUES ('Dell', 'USA', 10000, 'Manufacturer of Dell noteBooks','just logo');";
        FileInputStream fis = null;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(fillStr1)
        ) {
            connection.setAutoCommit(false);

            ps.setString(1, "Dell");
            ps.setString(2, "USA");
            ps.setInt(3,10000);
            ps.setString(4,"Manufacturer of Dell noteBooks");
            ps.setString(5,"just logo");
            fis = new FileInputStream("src/main/webapp/img/book_dell.jpg");
            ps.setBinaryStream(6, fis);
            ps.addBatch();

            ps.setString(1, "HP");
            ps.setString(2, "USA");
            ps.setInt(3,20000);
            ps.setString(4,"Manufacturer of HP noteBooks");
            ps.setString(5,"just logo");
            fis = new FileInputStream("src/main/webapp/img/book_hp.jpg");
            ps.setBinaryStream(6, fis);
            ps.addBatch();

            ps.setString(1, "Gnusmas");
            ps.setString(2, "South Korea");
            ps.setInt(3,20000);
            ps.setString(4,"Manufacturer of Samsung noteBooks");
            ps.setString(5,"just logo");
            fis = new FileInputStream("src/main/webapp/img/book_samsung.jpg");
            ps.setBinaryStream(6, fis);
            ps.addBatch();

            int rows[] = ps.executeBatch();
            System.out.println("to notebookdev_tbl where added " + (rows.length) +" record(s)");
            connection.commit();

            fis.close();
        }
        catch (SQLException  e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void fillDevHistory_tbl(){

        String fillStr1 =
                "INSERT notebookdev_db.devhistory_tbl(devid, devhistory) \n" +
                        "VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(fillStr1);
        ) {
            connection.setAutoCommit(false);


            ps.setInt(1, 1);
            ps.setString(2, "short story №1 about Dell");
            ps.addBatch();

            ps.setInt(1, 1);
            ps.setString(2, "short story №2 about Dell");
            ps.addBatch();

            ps.setInt(1, 2);
            ps.setString(2, "short story №1 about HP");
            ps.addBatch();

            ps.setInt(1, 2);
            ps.setString(2, "short story №2 about HP");
            ps.addBatch();

            ps.setInt(1, 3);
            ps.setString(2, "short story №1 about Gnusmas");
            ps.addBatch();

            ps.setInt(1, 3);
            ps.setString(2, "short story №2 about Gnusmas");
            ps.addBatch();


            int rows[] = ps.executeBatch();
            System.out.println("to devhistory_tbl where added " + (rows.length) +" record(s)");
            connection.commit();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fillDevHistoryPhoto_tbl(){
        String fillStr1 =
                "INSERT notebookdev_db.devhistory_photo_tbl(devhistoryId, photo) \n" +
                        "VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(fillStr1);
        ) {
            connection.setAutoCommit(false);
            FileInputStream fis;

            ps.setInt(1,1);
            fis = new FileInputStream("src/main/webapp/img/historyPhoto/book_dell.jpg");
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,2);
            fis = new FileInputStream("src/main/webapp/img/historyPhoto/book_dell.jpg");
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,3);
            fis = new FileInputStream("src/main/webapp/img/historyPhoto/book_dell.jpg");
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,4);
            fis = new FileInputStream("src/main/webapp/img/historyPhoto/book_dell.jpg");
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,5);
            fis = new FileInputStream("src/main/webapp/img/historyPhoto/book_dell.jpg");
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,6);
            fis = new FileInputStream("src/main/webapp/img/historyPhoto/book_dell.jpg");
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            int rows[] = ps.executeBatch();
            System.out.println("to devhistory_photo_tbl where added " + (rows.length) +" record(s)");
            connection.commit();

            fis.close();

        }
        catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fillTables(){
        fillNotebookdev();
        fillDevHistory_tbl();
        fillDevHistoryPhoto_tbl();
    }
}
