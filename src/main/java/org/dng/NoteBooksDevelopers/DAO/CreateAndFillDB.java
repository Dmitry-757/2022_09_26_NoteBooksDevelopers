package org.dng.NoteBooksDevelopers.DAO;


import jakarta.servlet.ServletContext;

public class CreateAndFillDB {
    private static boolean dbInitialized;
    public static void main(String[] args) {
        makeDB(null);
    }

    public static void makeDB(ServletContext ctx){
        if (!dbInitialized) {
            PrepareDB.prepareBase();
            PrepareDB.createTables();
            PrepareDB.fillTables(ctx);
            dbInitialized = true;
        }
    }
}
