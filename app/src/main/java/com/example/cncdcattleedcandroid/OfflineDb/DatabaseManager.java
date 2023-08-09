package com.example.cncdcattleedcandroid.OfflineDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {


    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;


    public DatabaseManager(Context context) {
        this.context = context;
    }

    public DatabaseManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    // Insert data into Provisions table
    public long insertProvision(String name, String description) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PROVISION_NAME, name);
        values.put(DatabaseHelper.COLUMN_PROVISION_DESCRIPTION, description);
        return database.insert(DatabaseHelper.TABLE_PROVISIONS, null, values);
    }

    // Get all Provisions
    public Cursor getAllProvisions() {
        return database.query(DatabaseHelper.TABLE_PROVISIONS, null, null, null, null, null, null);
    }

    // Insert data into Cities table
    public long insertCity(String name, int population) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CITY_NAME, name);
        values.put(DatabaseHelper.COLUMN_CITY_POPULATION, population);
        return database.insert(DatabaseHelper.TABLE_CITIES, null, values);
    }

    // Get all Cities
    public Cursor getAllCities() {
        return database.query(DatabaseHelper.TABLE_CITIES, null, null, null, null, null, null);
    }

    // Insert data into Barcodes table
    public long insertBarcode(String code, String productName) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BARCODE_CODE, code);
        values.put(DatabaseHelper.COLUMN_BARCODE_PRODUCT_NAME, productName);
        return database.insert(DatabaseHelper.TABLE_BARCODES, null, values);
    }

    // Get all Barcodes
    public Cursor getAllBarcodes() {
        return database.query(DatabaseHelper.TABLE_BARCODES, null, null, null, null, null, null);
    }

    // Insert data into Questions table
    public long insertQuestion(String questionText, String answer) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_QUESTION_TEXT, questionText);
        values.put(DatabaseHelper.COLUMN_QUESTION_ANSWER, answer);
        return database.insert(DatabaseHelper.TABLE_QUESTIONS, null, values);
    }

    // Get all Questions
    public Cursor getAllQuestions() {
        return database.query(DatabaseHelper.TABLE_QUESTIONS, null, null, null, null, null, null);
    }
}
