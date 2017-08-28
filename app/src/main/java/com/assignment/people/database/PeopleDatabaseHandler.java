package com.assignment.people.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by affandy on 25/08/2017.
 */

class PeopleDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "people.db";
    private static final int DATABASE_VERSION = 1;

    static final String TABLE_PEOPLES = "peoples";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_DESCRIPTION = "description";
    static final String COLUMN_DATE_CREATED = "created";
    static final String COLUMN_DATE_UPDATED= "updated";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PEOPLES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_DATE_CREATED + " NUMERIC, " +
                    COLUMN_DATE_UPDATED + " NUMERIC "+
                    ")";


    PeopleDatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PEOPLES);
        db.execSQL(TABLE_CREATE);
    }
}
