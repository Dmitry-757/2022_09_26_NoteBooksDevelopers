package org.dng.NoteBooksDevelopers.DAO;


public class CreateAndFillDB {
    public static void main(String[] args) {
        makeDB();
    }

    public static void makeDB(){
        PrepareDB.prepareBase();
        PrepareDB.createTables();
        PrepareDB.fillTables();
    }
}
