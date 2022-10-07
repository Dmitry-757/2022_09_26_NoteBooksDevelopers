package org.dng.NoteBooksDevelopers.DAO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
                """
                        CREATE TABLE notebookdev_db.devhistory_tbl (
                          id INT NOT NULL AUTO_INCREMENT,
                          devId INT NOT NULL,
                          devhistory VARCHAR(1500) NULL,
                          PRIMARY KEY (id),
                          UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
                          INDEX id_idx (devId ASC) VISIBLE,
                          CONSTRAINT devIdKey
                            FOREIGN KEY (devId)
                            REFERENCES notebookdev_db.notebookdev_tbl (id)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE);""";

        String CREATE_TABLE_devHistoryPhoto_tbl =
                """
                        CREATE TABLE notebookdev_db.devhistory_photo_tbl (
                          id INT NOT NULL AUTO_INCREMENT,
                          photo MEDIUMBLOB NOT NULL,
                          devhistoryId INT NOT NULL,
                          PRIMARY KEY (id),
                          UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
                          INDEX historyKey_idx (devhistoryId ASC) VISIBLE,
                          CONSTRAINT historyKey
                            FOREIGN KEY (devhistoryId)
                            REFERENCES notebookdev_db.devhistory_tbl (id)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE);""";


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
        FileInputStream fis;
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

            int[] rows = ps.executeBatch();
            System.out.println("to notebookdev_tbl where added " + (rows.length) +" record(s)");
            connection.commit();

            fis.close();
        }
        catch (SQLException  e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void fillDevHistory_tbl(){

        String fillStr1 =
                "INSERT notebookdev_db.devhistory_tbl(devid, devhistory) \n" +
                        "VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(fillStr1)
        ) {
            connection.setAutoCommit(false);


            //Dell
            ps.setInt(1, 1);
            ps.setString(2, "Где и когда появился первый ноутбук");
            ps.addBatch();

            ps.setInt(1, 1);
            ps.setString(2, "1980-е. Эксперименты с формой");
            ps.addBatch();

            ps.setInt(1, 1);
            ps.setString(2, "1990-е. Все становится привычным");
            ps.addBatch();

            ps.setInt(1, 1);
            ps.setString(2, "2020-е. Технологические революции");
            ps.addBatch();

            //HP
            ps.setInt(1, 2);
            ps.setString(2, " Почти ПК: программируемый калькулятор HP 9100A");
            ps.addBatch();

            ps.setInt(1, 2);
            ps.setString(2, "В 1993 году появился HP OmniBook 300 – суперпортативный компьютер, " +
                    "размеры которого сравнимы с современными нетбуками. Для того времени это было настоящим прорывом, " +
                    "никто и представить себе не мог, что полноценный компьютер может быть таким компактным. " +
                    "При этом в нем трудился полноценный процессор Intel 386, " +
                    "имелись 9-дюймовый дисплей и батарея для автономной работы. " +
                    "HP OmniBook 300 не был очень мощным даже по меркам своего времени, " +
                    "но повседневные задачи решал без особых проблем. Покупателями HP OmniBook 300 были бизнесмены, " +
                    "которых прельщала возможность работать в дороге и не смущала достаточно высокая цена в $2000");
            ps.addBatch();


            //Samsung
            ps.setInt(1, 3);
            ps.setString(2, "Первым лэптопом Samsung стал S5200, выпущенный в 1989 году для корейского рынка");
            ps.addBatch();

            ps.setInt(1, 3);
            ps.setString(2, "В 2005 году появился трансформер Samsung M70. " +
                    "Дисплей ноутбука мог быть отсоединен и прикреплен к подставке, что превращало его в элегантные шорты домашний компьютер");
            ps.addBatch();

            ps.setInt(1, 3);
            ps.setString(2, "В штатах продажи ноутбуков Samsung начались совсем недавно — в 2009 году. " +
                    "Первым в продаже появился 13-дюймовый Samsung Q310 в двух версиях. " +
                    "Младшая модель оснащалась процессором Intel Core 2 Duo T5800 2.0GHz и жестким диском на 250 Гб, " +
                    "старшая — Intel Core 2 Duo P8400 с 320 Гб памяти.");
            ps.addBatch();




            int[] rows = ps.executeBatch();
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
             PreparedStatement ps = connection.prepareStatement(fillStr1)
        ) {
            connection.setAutoCommit(false);
            FileInputStream fis;

            //dell
            ps.setInt(1,1);
            fis = new FileInputStream("src/main/webapp/img/historyPhoto/dell_1.jpg");
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,2);
            fis = new FileInputStream("src/main/webapp/img/historyPhoto/dell_2.jpg");
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,3);
            fis = new FileInputStream("src/main/webapp/img/historyPhoto/dell_3.jpg");
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            //hp
            ps.setInt(1,5);
            fis = new FileInputStream("src/main/webapp/img/historyPhoto/hp_1.jpg");
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,6);
            fis = new FileInputStream("src/main/webapp/img/historyPhoto/hp_2.jpg");
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            //samsung
            ps.setInt(1,7);
            fis = new FileInputStream("src/main/webapp/img/historyPhoto/samsung_1.jpg");
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,8);
            fis = new FileInputStream("src/main/webapp/img/historyPhoto/samsung_2.jpg");
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,9);
            fis = new FileInputStream("src/main/webapp/img/historyPhoto/samsung_3.jpg");
            ps.setBinaryStream(2, fis);
            ps.addBatch();


            int[] rows = ps.executeBatch();
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
