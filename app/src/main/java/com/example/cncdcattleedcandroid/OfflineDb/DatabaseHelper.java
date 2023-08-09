package com.example.cncdcattleedcandroid.OfflineDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    // Table names
    public static final String TABLE_PROVISIONS = "Provisions";
    public static final String TABLE_CITIES = "Cities";
    public static final String TABLE_BARCODES = "Barcodes";
    public static final String TABLE_QUESTIONS = "Questions";

    // Column names
    public static final String COLUMN_ID = "id";

    // Provisions table columns
    public static final String COLUMN_PROVISION_NAME = "name";
    public static final String COLUMN_PROVISION_DESCRIPTION = "description";

    // Cities table columns
    public static final String COLUMN_CITY_NAME = "name";
    public static final String COLUMN_CITY_POPULATION = "population";

    // Barcodes table columns
    public static final String COLUMN_BARCODE_CODE = "code";
    public static final String COLUMN_BARCODE_PRODUCT_NAME = "product_name";

    // Questions table columns
    public static final String COLUMN_QUESTION_TEXT = "question_text";
    public static final String COLUMN_QUESTION_ANSWER = "answer";

    public static final String COLUMN_NUMBER_OF_CATTLES = "no_of_cattles";

    public static final String COLUMN_NUMBER_OF_COWS = "no_of_cows";

    public static final String COLUMN_NUMBER_OF_BUFFAL = "no_of_buffalos";


    // Create tables queries
    private static final String CREATE_TABLE_PROVISIONS = "CREATE TABLE " + TABLE_PROVISIONS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_PROVISION_NAME + " TEXT NOT NULL "
            + ")";

    private static final String CREATE_TABLE_CITIES = "CREATE TABLE " + TABLE_CITIES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_CITY_NAME + " TEXT NOT NULL "
            + ")";

    private static final String CREATE_TABLE_BARCODES = "CREATE TABLE " + TABLE_BARCODES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_BARCODE_CODE + " TEXT NOT NULL,"
            + COLUMN_BARCODE_PRODUCT_NAME + " TEXT"
            + ")";

    private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE " + TABLE_QUESTIONS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_QUESTION_TEXT + " TEXT NOT NULL,"
            + COLUMN_QUESTION_ANSWER + " TEXT"
            + ")";


    private static final String CREATE_TABLE_APPDATA = "CREATE TABLE " + TABLE_QUESTIONS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_NUMBER_OF_COWS + " TEXT NOT NULL,"
            + COLUMN_QUESTION_ANSWER + " TEXT"
            + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROVISIONS);
        db.execSQL(CREATE_TABLE_CITIES);
        db.execSQL(CREATE_TABLE_BARCODES);
        db.execSQL(CREATE_TABLE_QUESTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
    }
}
