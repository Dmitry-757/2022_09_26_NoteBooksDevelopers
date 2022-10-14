package org.dng.NoteBooksDevelopers.DAO;


public class CreateAndFillDB {
    private static boolean dbInitialized;
    public static void main(String[] args) {
        makeDB();
    }

    public static void makeDB(){
        if (!dbInitialized) {
            PrepareDB.prepareBase();
            PrepareDB.createTables();
            PrepareDB.fillTables();
            dbInitialized = true;
        }
    }
}
