package org.dng.NoteBooksDevelopers.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PrepareDB {
    public static void prepareBase() {
        String PREPARE_QUERY =
                "DROP DATABASE IF EXISTS `notebookdev_db`;"
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
                        "CREATE DATABASE IF NOT EXISTS `notebookdev_db` ;"
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
                "CREATE TABLE `notebookdev_db`.`notebookdev_tbl` (" +
                        "  `id` INT NOT NULL AUTO_INCREMENT," +
                        "  `name` VARCHAR(50) NOT NULL," +
                        "  `country` VARCHAR(45) NULL," +
                        "  `employeesNumber` INT NULL," +
                        "  `shortInfo` VARCHAR(300) NULL," +
                        "  `logo` VARCHAR(45) NULL," +
                        "  `photo`   MEDIUMBLOB null," +
                        "  PRIMARY KEY (`id`)," +
                        "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";


        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.addBatch(CREATE_TABLE_notebookdev_tbl);
//            statement.addBatch(CREATE_TABLE_Services);

            statement.executeBatch();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fillTable(){

        String SELECT_DB =
                "USE notebookdev_db; ";

        String fillStr1 =
                "INSERT notebookdev_tbl(name, country, employeesNumber, shortInfo, logo, photo) \n" +
                        "VALUES (?, ?, ?, ?, ?, ?);";
//                        "VALUES ('Dell', 'USA', 10000, 'Manufacturer of Dell noteBooks','just logo');";

        String fillStr2 = "INSERT notebookdev_tbl(name, country, employeesNumber, shortInfo, logo) \n" +
                        "VALUES ('HP', 'USA', 20000, 'Manufacturer of HP noteBooks','just logo');";
        String fillStr3 = "INSERT notebookdev_tbl(name, country, employeesNumber, shortInfo, logo) \n" +
                "VALUES ('Gnusmas', 'South Korea', 30000, 'Manufacturer of Samsung noteBooks','just logo');";

        try (Connection connection = DBConnection.getConnection();
             //Statement statement = connection.createStatement();
             PreparedStatement ps = connection.prepareStatement(fillStr1)
        ) {
            ps.addBatch(SELECT_DB);

            FileInputStream fis;

            ps.setString(1, "Dell");
            ps.setString(2, "USA");
            ps.setInt(3,10000);
            ps.setString(4,"Manufacturer of Dell noteBooks");
            ps.setString(5,"just logo");





            //            fis = new FileInputStream("/img/book_dell.jpg");
//            File directory = new File("./src/main/webapp/img");
//            System.out.println(directory.getAbsolutePath());
//            Path file = Path.of("src/main/webapp/img/notebook.jpg");
            fis = new FileInputStream("src/main/webapp/img/notebook.jpg");
            ps.setBinaryStream(6, fis);
            ps.addBatch(fillStr1);

//            ps.setString(1, "Dell");
//            ps.setString(2, "USA");
//            ps.setInt(3,20000);
//            ps.setString(4,"Manufacturer of HP NoteBooks");
//            ps.setString(5,"just logo");
//            fis = new FileInputStream("/img/book_hp.jpg");
//            ps.setBinaryStream(5, fis);
//            statement.addBatch(fillStr1);
//
//            ps.setString(1, "Gnusmas");
//            ps.setString(2, "South Korea");
//            ps.setInt(3,30000);
//            ps.setString(4,"Manufacturer of Samsung noteBooks");
//            ps.setString(5,"just logo");
//            fis = new FileInputStream("/img/book_samsung.jpg");
//            ps.setBinaryStream(5, fis);

            ps.executeBatch();

//            statement.addBatch(fillStr1);
//            statement.addBatch(fillStr2);
//            statement.addBatch(fillStr3);

//            statement.executeBatch();

        }
        catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
