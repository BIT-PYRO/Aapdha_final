package com.example.apadha_final;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class ReportsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reports.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "reports";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DISASTER = "disaster";
    public static final String COLUMN_MEDIA_URI = "media_uri";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public ReportsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REPORTS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DISASTER + " TEXT, " +
                COLUMN_MEDIA_URI + " TEXT, " +
                COLUMN_TIMESTAMP + " TEXT)";
        db.execSQL(CREATE_REPORTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to add a report and return the row ID
    public long addReport(String disaster, String mediaUri, String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DISASTER, disaster);
        values.put(COLUMN_MEDIA_URI, mediaUri);
        values.put(COLUMN_TIMESTAMP, timestamp);

        // Insert the report into the table and return the row ID
        long rowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return rowId;
    }

    // Method to get all reports (sorted by timestamp in descending order)
    public Cursor getAllReports() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, COLUMN_TIMESTAMP + " DESC");
    }
}
