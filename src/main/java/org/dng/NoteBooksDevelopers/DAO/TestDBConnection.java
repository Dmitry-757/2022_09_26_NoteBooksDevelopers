package org.dng.NoteBooksDevelopers.DAO;


public class TestDBConnection {
    public static void main(String[] args) {
        PrepareDB.prepareBase();
        PrepareDB.createTables();
        PrepareDB.fillTable();
    }
}
