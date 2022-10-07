package org.dng.NoteBooksDevelopers.DAO;


public class CreateAndFillDB {
    public static void main(String[] args) {
        PrepareDB.prepareBase();
        PrepareDB.createTables();
        PrepareDB.fillTables();
    }
}
