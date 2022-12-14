package org.dng.NoteBooksDevelopers.DAO;

import jakarta.servlet.ServletContext;

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
                """
                          CREATE TABLE notebookdev_db.notebookdev_tbl (
                          id INT NOT NULL AUTO_INCREMENT,
                          name VARCHAR(50) NOT NULL,
                          country VARCHAR(45) NULL,
                          employeesNumber INT NULL,
                          shortInfo VARCHAR(300) NULL,
                          logo VARCHAR(45) NULL,
                          photo MEDIUMBLOB NOT NULL,
                          PRIMARY KEY (id),
                          UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE);""";


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

        String CREATE_TABLE_shortNews_tbl =
                """
                        CREATE TABLE notebookdev_db.shortnews_tbl (
                          id INT NOT NULL AUTO_INCREMENT,
                          shortNews VARCHAR(1000) NULL,
                          photo MEDIUMBLOB NOT NULL,
                          PRIMARY KEY (id),
                          UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE);""";

//        String CREATE_TABLE_NewsPhoto_tbl =
//                """
//                        CREATE TABLE notebookdev_db.news_photo_tbl (
//                          id INT NOT NULL AUTO_INCREMENT,
//                          photo MEDIUMBLOB NOT NULL,
//                          newsId INT NOT NULL,
//                          PRIMARY KEY (id),
//                          UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
//                          INDEX newsKey_idx (newsId ASC) VISIBLE,
//                          CONSTRAINT newsKey
//                            FOREIGN KEY (newsId)
//                            REFERENCES notebookdev_db.shortnews_tbl (id)
//                            ON DELETE CASCADE
//                            ON UPDATE CASCADE);
//                            """;

        String CREATE_TABLE_DetailedNews_tbl =
                """
                        CREATE TABLE notebookdev_db.detailed_news_tbl (
                          id INT NOT NULL AUTO_INCREMENT,
                          shortNewsId INT NOT NULL,
                          news VARCHAR(500) NULL,
                          PRIMARY KEY (id),
                          UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
                          INDEX shortNewsKey_idx (shortNewsId ASC) VISIBLE,
                          CONSTRAINT shortNewsKey
                            FOREIGN KEY (shortNewsId)
                            REFERENCES notebookdev_db.shortnews_tbl (id)
                            ON DELETE NO ACTION
                            ON UPDATE NO ACTION);""";

        String CREATE_TABLE_DetailedNewsPhoto_tbl =
                """
                        CREATE TABLE `notebookdev_db`.`detailednews_photo_tbl` (
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `detailedNewsId` INT NOT NULL,
                          `photo` MEDIUMBLOB NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
                          INDEX `dnewskey_idx` (`detailedNewsId` ASC) VISIBLE,
                          CONSTRAINT `dnewskey`
                            FOREIGN KEY (`detailedNewsId`)
                            REFERENCES `notebookdev_db`.`detailed_news_tbl` (`id`)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE);""";


        String CREATE_TABLE_Models_tbl =
                """
                        CREATE TABLE notebookdev_db.models_tbl (
                          id INT NOT NULL AUTO_INCREMENT,
                          devId INT NOT NULL,
                          modelName VARCHAR(500) NULL,
                          photo MEDIUMBLOB NULL,
                          PRIMARY KEY (id),
                          UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
                          INDEX id_idx (devId ASC) VISIBLE,
                          CONSTRAINT models2devIdKey
                            FOREIGN KEY (devId)
                            REFERENCES notebookdev_db.notebookdev_tbl (id)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE);""";

        String CREATE_TABLE_DetailedModels =
                """
                        CREATE TABLE `notebookdev_db`.`models_detailed_tbl` (
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `modelsId` INT NOT NULL,
                          `description` VARCHAR(500) NOT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);
                        
                        """;

        String CREATE_TABLE_DetailedModelsPhoto = """
                        CREATE TABLE `notebookdev_db`.`models_detailed_photo_tbl` (
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `detailedModelId` INT NOT NULL,
                          `photo` MEDIUMBLOB NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
                          INDEX `detailedModelsKey_idx` (`detailedModelId` ASC) VISIBLE,
                          CONSTRAINT `detailedModelsKey`
                            FOREIGN KEY (`detailedModelId`)
                            REFERENCES `notebookdev_db`.`models_detailed_tbl` (`id`)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE);                
                        """;

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.addBatch(CREATE_TABLE_notebookdev_tbl);
            statement.addBatch(CREATE_TABLE_devHistory_tbl);
            statement.addBatch(CREATE_TABLE_devHistoryPhoto_tbl);
            statement.addBatch(CREATE_TABLE_shortNews_tbl);
//            statement.addBatch(CREATE_TABLE_NewsPhoto_tbl);
            statement.addBatch(CREATE_TABLE_DetailedNews_tbl);
            statement.addBatch(CREATE_TABLE_DetailedNewsPhoto_tbl);
            statement.addBatch(CREATE_TABLE_Models_tbl);
            statement.addBatch(CREATE_TABLE_DetailedModels);
            statement.addBatch(CREATE_TABLE_DetailedModelsPhoto);

            statement.executeBatch();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void fillNotebookdev(ServletContext ctx){
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
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/developers/book_dell.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/developers/book_dell.jpg"));
            }

            ps.setBinaryStream(6, fis);
            ps.addBatch();

            ps.setString(1, "HP");
            ps.setString(2, "USA");
            ps.setInt(3,20000);
            ps.setString(4,"Manufacturer of HP noteBooks");
            ps.setString(5,"just logo");
//            fis = new FileInputStream("src/main/webapp/img/developers/book_hp.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/developers/book_hp.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/developers/book_hp.jpg"));
            }
            ps.setBinaryStream(6, fis);
            ps.addBatch();

            ps.setString(1, "Gnusmas");
            ps.setString(2, "South Korea");
            ps.setInt(3,20000);
            ps.setString(4,"Manufacturer of Samsung noteBooks");
            ps.setString(5,"just logo");
//            fis = new FileInputStream("src/main/webapp/img/developers/book_samsung.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/developers/book_samsung.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/developers/book_samsung.jpg"));
            }
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
            ps.setString(2, "?????? ?? ?????????? ???????????????? ???????????? ??????????????");
            ps.addBatch();

            ps.setInt(1, 1);
            ps.setString(2, "1980-??. ???????????????????????? ?? ????????????");
            ps.addBatch();

            ps.setInt(1, 1);
            ps.setString(2, "1990-??. ?????? ???????????????????? ??????????????????");
            ps.addBatch();

            ps.setInt(1, 1);
            ps.setString(2, "2020-??. ?????????????????????????????? ??????????????????");
            ps.addBatch();

            //HP
            ps.setInt(1, 2);
            ps.setString(2, " ?????????? ????: ?????????????????????????????? ?????????????????????? HP 9100A");
            ps.addBatch();

            ps.setInt(1, 2);
            ps.setString(2, "?? 1993 ???????? ???????????????? HP OmniBook 300 ??? ???????????????????????????????? ??????????????????, " +
                    "?????????????? ???????????????? ???????????????? ?? ???????????????????????? ??????????????????. ?????? ???????? ?????????????? ?????? ???????? ?????????????????? ????????????????, " +
                    "?????????? ?? ?????????????????????? ???????? ???? ??????, ?????? ?????????????????????? ?????????????????? ?????????? ???????? ?????????? ????????????????????. " +
                    "?????? ???????? ?? ?????? ???????????????? ?????????????????????? ?????????????????? Intel 386, " +
                    "?????????????? 9-???????????????? ?????????????? ?? ?????????????? ?????? ???????????????????? ????????????. " +
                    "HP OmniBook 300 ???? ?????? ?????????? ???????????? ???????? ???? ???????????? ???????????? ??????????????, " +
                    "???? ???????????????????????? ???????????? ?????????? ?????? ???????????? ??????????????. ???????????????????????? HP OmniBook 300 ???????? ????????????????????, " +
                    "?????????????? ?????????????????? ?????????????????????? ???????????????? ?? ???????????? ?? ???? ?????????????? ???????????????????? ?????????????? ???????? ?? $2000");
            ps.addBatch();


            //Samsung
            ps.setInt(1, 3);
            ps.setString(2, "???????????? ???????????????? Samsung ???????? S5200, ???????????????????? ?? 1989 ???????? ?????? ???????????????????? ??????????");
            ps.addBatch();

            ps.setInt(1, 3);
            ps.setString(2, "?? 2005 ???????? ???????????????? ?????????????????????? Samsung M70. " +
                    "?????????????? ???????????????? ?????? ???????? ???????????????????? ?? ???????????????????? ?? ??????????????????, ?????? ???????????????????? ?????? ?? ???????????????????? ?????????? ???????????????? ??????????????????");
            ps.addBatch();

            ps.setInt(1, 3);
            ps.setString(2, "?? ???????????? ?????????????? ?????????????????? Samsung ???????????????? ???????????? ?????????????? ??? ?? 2009 ????????. " +
                    "???????????? ?? ?????????????? ???????????????? 13-???????????????? Samsung Q310 ?? ???????? ??????????????. " +
                    "?????????????? ???????????? ???????????????????? ?????????????????????? Intel Core 2 Duo T5800 2.0GHz ?? ?????????????? ???????????? ???? 250 ????, " +
                    "?????????????? ??? Intel Core 2 Duo P8400 ?? 320 ???? ????????????.");
            ps.addBatch();


            int[] rows = ps.executeBatch();
            System.out.println("to devhistory_tbl where added " + (rows.length) +" record(s)");
            connection.commit();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fillDevHistoryPhoto_tbl(ServletContext ctx){
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
//            fis = new FileInputStream("src/main/webapp/img/historyPhoto/dell_1_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/historyPhoto/dell_1_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/historyPhoto/dell_1_1.jpg"));
            }

            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,2);
//            fis = new FileInputStream("src/main/webapp/img/historyPhoto/dell_1_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/historyPhoto/dell_1_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/historyPhoto/dell_1_2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,3);
//            fis = new FileInputStream("src/main/webapp/img/historyPhoto/dell_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/historyPhoto/dell_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/historyPhoto/dell_2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,4);
//            fis = new FileInputStream("src/main/webapp/img/historyPhoto/dell_3.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/historyPhoto/dell_3.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/historyPhoto/dell_3.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            //hp
            ps.setInt(1,5);
//            fis = new FileInputStream("src/main/webapp/img/historyPhoto/hp_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/historyPhoto/hp_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/historyPhoto/hp_1.jpg"));
            }

            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,6);
//            fis = new FileInputStream("src/main/webapp/img/historyPhoto/hp_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/historyPhoto/hp_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/historyPhoto/hp_2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            //samsung
            ps.setInt(1,7);
//            fis = new FileInputStream("src/main/webapp/img/historyPhoto/samsung_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/historyPhoto/samsung_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/historyPhoto/samsung_1.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,8);
//            fis = new FileInputStream("src/main/webapp/img/historyPhoto/samsung_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/historyPhoto/samsung_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/historyPhoto/samsung_2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,9);
//            fis = new FileInputStream("src/main/webapp/img/historyPhoto/samsung_3.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/historyPhoto/samsung_3.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/historyPhoto/samsung_3.jpg"));
            }
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







    public static void fillNews_tbl(ServletContext ctx){

        String fillStr1 =
                "INSERT notebookdev_db.shortnews_tbl(shortNews, photo) \n" +
                        "VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(fillStr1)
        ) {
            connection.setAutoCommit(false);
            FileInputStream fis;

            ps.setString(1, "?????????????? 1");
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/n1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/newsPhoto/n1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/newsPhoto/n1.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setString(1, "?????????????? 2");
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/n2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/newsPhoto/n2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/newsPhoto/n2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setString(1, "?????????????? 3");
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/n3.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/newsPhoto/n3.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/newsPhoto/n3.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setString(1, "?????????????? 4");
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/n4.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/newsPhoto/n4.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/newsPhoto/n4.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            int[] rows = ps.executeBatch();
            System.out.println("to news_tbl where added " + (rows.length) +" record(s)");
            connection.commit();

        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void fillNewsPhoto_tbl(){
//        String fillStr1 =
//                "INSERT notebookdev_db.news_photo_tbl(newsId, photo) \n" +
//                        "VALUES (?, ?)";
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement ps = connection.prepareStatement(fillStr1)
//        ) {
//            connection.setAutoCommit(false);
//            FileInputStream fis;
//
//            ps.setInt(1,1);
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/n1.jpg");
//            ps.setBinaryStream(2, fis);
//            ps.addBatch();
//
//            ps.setInt(1,2);
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/n1.jpg");
//            ps.setBinaryStream(2, fis);
//            ps.addBatch();
//
//            ps.setInt(1,3);
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/n1.jpg");
//            ps.setBinaryStream(2, fis);
//            ps.addBatch();
//
//            int[] rows = ps.executeBatch();
//            System.out.println("to news_photo_tbl where added " + (rows.length) +" record(s)");
//            connection.commit();
//
//            fis.close();
//
//        }
//        catch (SQLException | FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }



    public static void fillDetailedNews_tbl(){

        String fillStr1 =
                "INSERT notebookdev_db.detailed_news_tbl(shortNewsId, news) \n" +
                        "VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(fillStr1)
        ) {
            connection.setAutoCommit(false);

            ps.setInt(1, 1);
            ps.setString(2, "???????????????????????????????? ?????????????? 1");
            ps.addBatch();

            ps.setInt(1, 2);
            ps.setString(2, "???????????????????????????????? ?????????????? 2");
            ps.addBatch();

            ps.setInt(1, 3);
            ps.setString(2, "???????????????????????????????? ?????????????? 3");
            ps.addBatch();

            ps.setInt(1, 4);
            ps.setString(2, "???????????????????????????????? ?????????????? 4 - ?????????????????? ???????? - ?????????? ???????????????? ?? ????????!");
            ps.addBatch();

            int[] rows = ps.executeBatch();
            System.out.println("to detailed_news_tbl where added " + (rows.length) +" record(s)");
            connection.commit();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fillDetailedNews_photo_tbl(ServletContext ctx){
        String fillStr1 =
                "INSERT notebookdev_db.detailednews_photo_tbl(detailedNewsId, photo) \n" +
                        "VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(fillStr1)
        ) {
            connection.setAutoCommit(false);
            FileInputStream fis;

            ps.setInt(1,1);
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n1_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n1_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/newsPhoto/detailedNewsPhoto/n1_1.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();
            ps.setInt(1,1);
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n1_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n1_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/newsPhoto/detailedNewsPhoto/n1_2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,2);
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n2_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n2_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/newsPhoto/detailedNewsPhoto/n2_1.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();
            ps.setInt(1,2);
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n2_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n2_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/newsPhoto/detailedNewsPhoto/n2_2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,3);
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n3_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n3_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/newsPhoto/detailedNewsPhoto/n3_1.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();
            ps.setInt(1,3);
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n3_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n3_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/newsPhoto/detailedNewsPhoto/n3_2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();
            ps.setInt(1,3);
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n3_3.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n3_3.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/newsPhoto/detailedNewsPhoto/n3_3.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            ps.setInt(1,4);
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n4_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n4_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/newsPhoto/detailedNewsPhoto/n4_1.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();
            ps.setInt(1,4);
//            fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n4_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/newsPhoto/detailedNewsPhoto/n4_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/newsPhoto/detailedNewsPhoto/n4_2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();


            int[] rows = ps.executeBatch();
            System.out.println("to detailedNews_photo_tbl where added " + (rows.length) +" record(s)");
            connection.commit();

            fis.close();

        }
        catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void fillModels_tbl(ServletContext ctx){
        String fillStr1 =
                "INSERT notebookdev_db.models_tbl(devId, modelName, photo) \n" +
                        "VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(fillStr1)
        ) {
            connection.setAutoCommit(false);
            FileInputStream fis;

            //dell
            ps.setInt(1,1); //devId
            ps.setString(2, "dell Full HD (1920x1080), IPS, Intel Core i3-1115G4, ????????: 2 ?? 3 ??????, " +
                    "RAM 8 ????, SSD 256 ????, Intel UHD Graphics , Linux");
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/dell_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/dell_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/dell_1.jpg"));
            }
            ps.setBinaryStream(3, fis);
            ps.addBatch();

            ps.setInt(1,1); //devId
            ps.setString(2, "dell Full HD (1920x1080), WVA (TN+film), Intel Core i5-1035G1, " +
                    "????????: 4 ?? 1 ??????, RAM 8 ????, SSD 256 ????, Intel UHD Graphics , Linux");
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/dell_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/dell_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/dell_2.jpg"));
            }
            ps.setBinaryStream(3, fis);
            ps.addBatch();

            //hp
            ps.setInt(1,2); //devId
            ps.setString(2, "hp 15.6\" ?????????????? Haier U1520HD ???????????? [Full HD (1920x1080), IPS, " +
                    "Intel Celeron N4020, ????????: 2 ?? 1.1 ??????, RAM 4 ????, HDD 1000 ????, eMMC 64 ????, Intel HD Graphics , ?????? ????]");
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/hp_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/hp_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/hp_1.jpg"));
            }
            ps.setBinaryStream(3, fis);
            ps.addBatch();

            ps.setInt(1,2); //devId
            ps.setString(2, "hp " +
                    "Full HD (1920x1080), IPS, Intel Pentium Silver N6000, ????????: 4 ?? 1.1 ??????, RAM 4 ????, " +
                    "SSD 128 ????, Intel UHD Graphics , Windows 10 Home Single Language");
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/hp_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/hp_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/hp_2.jpg"));
            }
            ps.setBinaryStream(3, fis);
            ps.addBatch();

            //samsung
            ps.setInt(1,3); //devId
            ps.setString(2, "samsung 15-???????????????? ?????????????? Samsung 900X4C-A01 ??? ????????????????????????, ????????????, " +
                    "?????????????? ?? ???????????????????? ?????????????????? ??????????????????. ?????? ???????????? ???????????????? ???? ?????????????? ?????????????????? ??? ??????????????????????, " +
                    "?????????????? ???????????????????????? ???????????????????????? ?? ?????????????????? ????????????????????. " +
                    "Samsung 900X4C ???????????? ???? ???????? ?????????????????????????? ???????????????????? Intel Core i5-3317U, ?????????????? 8 ???? ?????????????????????? " +
                    "???????????? ?? ???????????????????? ?????????????????????? ?????????????????????? Intel HD Graphics 4000");
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/samsung_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/samsung_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/samsung_1.jpg"));
            }
            ps.setBinaryStream(3, fis);
            ps.addBatch();

            ps.setInt(1,3); //devId
            ps.setString(2, "samsung ?????????????? Samsung RF712 ???????????????? ?? ???????????????? ??????????????, " +
                    "???? ?????????????????? ???????????????????????? ?????????? ???????????? 3D-??????????????????????. " +
                    "?? ???????????? ???????????????????? 17,3-???????????????? HD-?????????????? ?? ?????????????????????? ??SuperBright Plus?? ?? LED-????????????????????, " +
                    "???? ???? 100% ???????? ?????????????? 3D-?????????????? ?? ???????????????????? ????????????, ????????????????????????????????, ???????????????????? ???????????????? ?? " +
                    "?????????????????????? ???????????????????????????? ?????? ?????????????????? ?? 3D-??????????.");
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/samsung_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/samsung_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/samsung_2.jpg"));
            }
            ps.setBinaryStream(3, fis);
            ps.addBatch();



            int[] rows = ps.executeBatch();
            System.out.println("to models_tbl where added " + (rows.length) +" record(s)");
            connection.commit();

            fis.close();

        }
        catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void fillModelsDetailed_tbl(){
        String fillStr1 =
                "INSERT notebookdev_db.models_detailed_tbl(modelsId, description) \n" +
                        "VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(fillStr1)
        ) {
            connection.setAutoCommit(false);

            //dell
            ps.setInt(1,1); //modelId
            ps.setString(2, "dell Full HD (1920x1080), IPS, Intel Core i3-1115G4, ????????: 2 ?? 3 ??????, " +
                    "RAM 8 ????, SSD 256 ????, Intel UHD Graphics , Linux");
            ps.addBatch();

            ps.setInt(1,2); //modelId
            ps.setString(2, "dell Full HD (1920x1080), WVA (TN+film), Intel Core i5-1035G1, " +
                    "????????: 4 ?? 1 ??????, RAM 8 ????, SSD 256 ????, Intel UHD Graphics , Linux");
            ps.addBatch();

            //hp
            ps.setInt(1,3); //modelId
            ps.setString(2, "hp 15.6\" ?????????????? Haier U1520HD ???????????? [Full HD (1920x1080), IPS, " +
                    "Intel Celeron N4020, ????????: 2 ?? 1.1 ??????, RAM 4 ????, HDD 1000 ????, eMMC 64 ????, Intel HD Graphics , ?????? ????]");
            ps.addBatch();

            ps.setInt(1,4); //modelId
            ps.setString(2, "hp " +
                    "Full HD (1920x1080), IPS, Intel Pentium Silver N6000, ????????: 4 ?? 1.1 ??????, RAM 4 ????, " +
                    "SSD 128 ????, Intel UHD Graphics , Windows 10 Home Single Language");
            ps.addBatch();

            //samsung
            ps.setInt(1,5); //modelId
            ps.setString(2, "samsung 15-???????????????? ?????????????? Samsung 900X4C-A01 ??? ????????????????????????, ????????????, " +
                    "?????????????? ?? ???????????????????? ?????????????????? ??????????????????. ?????? ???????????? ???????????????? ???? ?????????????? ?????????????????? ??? ??????????????????????, " +
                    "?????????????? ???????????????????????? ???????????????????????? ?? ?????????????????? ????????????????????. " +
                    "Samsung 900X4C ???????????? ???? ???????? ?????????????????????????? ???????????????????? Intel Core i5-3317U, ?????????????? 8 ???? ?????????????????????? " +
                    "???????????? ?? ???????????????????? ?????????????????????? ?????????????????????? Intel HD Graphics 4000");
            ps.addBatch();

            ps.setInt(1,6); //modelId
            ps.setString(2, "samsung ?????????????? Samsung RF712 ???????????????? ?? ???????????????? ??????????????, " +
                    "???? ?????????????????? ???????????????????????? ?????????? ???????????? 3D-??????????????????????. " +
                    "?? ???????????? ???????????????????? 17,3-???????????????? HD-?????????????? ?? ?????????????????????? ??SuperBright Plus?? ?? LED-????????????????????, " +
                    "???? ???? 100% ???????? ?????????????? 3D-?????????????? ?? ???????????????????? ????????????, ????????????????????????????????, ???????????????????? ???????????????? ?? " +
                    "?????????????????????? ???????????????????????????? ?????? ?????????????????? ?? 3D-??????????.");
            ps.addBatch();



            int[] rows = ps.executeBatch();
            System.out.println("to models_detailed_tbl where added " + (rows.length) +" record(s)");
            connection.commit();

        }
        catch (SQLException  e) {
            e.printStackTrace();
        }
    }


    public static void fillDetailedModels_photo_tbl(ServletContext ctx){
        String fillStr1 =
                "INSERT notebookdev_db.models_detailed_photo_tbl(detailedModelId, photo) \n" +
                        "VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(fillStr1)
        ) {
            connection.setAutoCommit(false);
            FileInputStream fis;

            //dell
            //2 photo to first model
            ps.setInt(1,1);
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/d1_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/d1_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/detailedModelsPhoto/d1_1.jpg"));
            }

            ps.setBinaryStream(2, fis);
            ps.addBatch();
            ps.setInt(1,1);
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/d1_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/d1_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/detailedModelsPhoto/d1_2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            //2 photo to second model
            ps.setInt(1,2);
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/d2_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/d2_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/detailedModelsPhoto/d2_1.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();
            ps.setInt(1,2);
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/d2_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/d2_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/detailedModelsPhoto/d2_2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            //hp
            //2 photo to third model
            ps.setInt(1,3);
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/hp1_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/hp1_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/detailedModelsPhoto/hp1_1.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();
            ps.setInt(1,3);
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/hp1_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/hp1_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/detailedModelsPhoto/hp1_2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            //2 photo to fourth model
            ps.setInt(1,4);
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/hp2_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/hp2_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/detailedModelsPhoto/hp2_1.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();
            ps.setInt(1,4);
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/hp2_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/hp2_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/detailedModelsPhoto/hp2_2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            //samsung
            //2 photo to faith model
            ps.setInt(1,5);
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/s1_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/s1_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/detailedModelsPhoto/s1_1.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();
            ps.setInt(1,5);
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/s1_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/s1_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/detailedModelsPhoto/s1_2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            //2 photo to sixth model
            ps.setInt(1,6);
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/s2_1.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/s2_1.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/detailedModelsPhoto/s2_1.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();
            ps.setInt(1,6);
//            fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/s2_2.jpg");
            if (ctx == null) {
                fis = new FileInputStream("src/main/webapp/img/modelsPhoto/detailedModelsPhoto/s2_2.jpg");
            }else {
                fis = new FileInputStream(ctx.getRealPath("/img/modelsPhoto/detailedModelsPhoto/s2_2.jpg"));
            }
            ps.setBinaryStream(2, fis);
            ps.addBatch();

            int[] rows = ps.executeBatch();
            System.out.println("to detailedModels_photo_tbl where added " + (rows.length) +" record(s)");
            connection.commit();

            fis.close();

        }
        catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void fillTables(ServletContext ctx){
        fillNotebookdev(ctx);
        fillDevHistory_tbl();
        fillDevHistoryPhoto_tbl(ctx);
        fillNews_tbl(ctx);
        //fillNewsPhoto_tbl();
        fillDetailedNews_tbl();
        fillDetailedNews_photo_tbl(ctx);
        fillModels_tbl(ctx);
        fillModelsDetailed_tbl();
        fillDetailedModels_photo_tbl(ctx);
    }
}
