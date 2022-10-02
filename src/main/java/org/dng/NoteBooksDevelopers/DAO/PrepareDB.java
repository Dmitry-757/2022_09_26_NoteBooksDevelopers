package org.dng.NoteBooksDevelopers.DAO;

import java.sql.Connection;
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
                "INSERT notebookdev_tbl(name, country, employeesNumber, shortInfo, logo) \n" +
                        "VALUES ('Dell', 'USA', 10000, 'Manufacturer of Dell noteBooks','just logo');";

        String fillStr2 = "INSERT notebookdev_tbl(name, country, employeesNumber, shortInfo, logo) \n" +
                        "VALUES ('HP', 'USA', 20000, 'Manufacturer of HP noteBooks','just logo');";
        String fillStr3 = "INSERT notebookdev_tbl(name, country, employeesNumber, shortInfo, logo) \n" +
                "VALUES ('Gnusmas', 'South Korea', 30000, 'Manufacturer of Samsung noteBooks','just logo');";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.addBatch(SELECT_DB);
            statement.addBatch(fillStr1);
            statement.addBatch(fillStr2);
            statement.addBatch(fillStr3);

            statement.executeBatch();
//            statement.execute(FILL_TABLE_notebookdev_tbl);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
