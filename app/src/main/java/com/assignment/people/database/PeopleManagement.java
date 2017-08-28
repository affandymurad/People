package com.assignment.people.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.assignment.people.model.People;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by affandy on 25/08/2017.
 */

public class PeopleManagement {
    private static final String LOGTAG = "PPL_MNGMNT";

    private SQLiteOpenHelper dbhandler;
    private SQLiteDatabase database;

    private static final String[] allColumns = {
            PeopleDatabaseHandler.COLUMN_ID,
            PeopleDatabaseHandler.COLUMN_NAME,
            PeopleDatabaseHandler.COLUMN_DESCRIPTION,
            PeopleDatabaseHandler.COLUMN_DATE_CREATED,
            PeopleDatabaseHandler.COLUMN_DATE_UPDATED
    };

    public PeopleManagement(Context context) {
        dbhandler = new PeopleDatabaseHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();


    }
    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();

    }

    public People addPeople(People people){
        ContentValues values  = new ContentValues();
        values.put(PeopleDatabaseHandler.COLUMN_NAME, people.getName());
        values.put(PeopleDatabaseHandler.COLUMN_DESCRIPTION, people.getDescription());
        values.put(PeopleDatabaseHandler.COLUMN_DATE_CREATED, people.getDateCreated());
        values.put(PeopleDatabaseHandler.COLUMN_DATE_UPDATED, people.getDateUpdated());
        long insertid = database.insert(PeopleDatabaseHandler.TABLE_PEOPLES,null,values);
        people.setId(insertid);
        return people;

    }

    // Getting single People
    public People getPeople(long id) {
        Cursor cursor = database.query(PeopleDatabaseHandler.TABLE_PEOPLES,allColumns,PeopleDatabaseHandler.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
        cursor.moveToFirst();
        People e = new People(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        // return People
        return e;
    }

    public List<People> getAllPeoples() {
        Cursor cursor = database.query(PeopleDatabaseHandler.TABLE_PEOPLES,allColumns,null,null,null, null, null);
        List<People> peoples = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                People people = new People();
                people.setId(cursor.getLong(cursor.getColumnIndex(PeopleDatabaseHandler.COLUMN_ID)));
                people.setName(cursor.getString(cursor.getColumnIndex(PeopleDatabaseHandler.COLUMN_NAME)));
                people.setDescription(cursor.getString(cursor.getColumnIndex(PeopleDatabaseHandler.COLUMN_DESCRIPTION)));
                people.setDateCreated(cursor.getString(cursor.getColumnIndex(PeopleDatabaseHandler.COLUMN_DATE_CREATED)));
                people.setDateUpdated(cursor.getString(cursor.getColumnIndex(PeopleDatabaseHandler.COLUMN_DATE_UPDATED)));
                peoples.add(people);
            }
        }
        // return All Peoples
        return peoples;
    }

    // Updating People
    public int updatePeople(People people) {
        ContentValues values = new ContentValues();
        values.put(PeopleDatabaseHandler.COLUMN_NAME, people.getName());
        values.put(PeopleDatabaseHandler.COLUMN_DESCRIPTION, people.getName());
        values.put(PeopleDatabaseHandler.COLUMN_DATE_CREATED, people.getDateCreated());
        values.put(PeopleDatabaseHandler.COLUMN_DATE_UPDATED, people.getDateUpdated());
        // updating row
        return database.update(PeopleDatabaseHandler.TABLE_PEOPLES, values,
                PeopleDatabaseHandler.COLUMN_ID + "=?",new String[] { String.valueOf(people.getId())});
    }

    // Deleting People
    public void removePeople(int Id) {
        try {
            database = dbhandler.getWritableDatabase();
            String query = "DELETE FROM " + PeopleDatabaseHandler.TABLE_PEOPLES + " WHERE " + PeopleDatabaseHandler.COLUMN_ID + " = " + Id;
            database.execSQL(query);
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
