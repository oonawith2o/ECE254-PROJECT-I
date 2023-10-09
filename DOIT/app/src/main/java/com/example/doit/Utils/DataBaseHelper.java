package com.example.doit.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doit.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    private static final String DATABASE_NAME = "DOIT_DATABASE";
    private static final String TABLE_NAME = "ITEMS_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "SUBJECT";
    private static final String COL_3 = "NOTE";
    private static final String COL_4 = "COMPLETED";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , SUBJECT TEXT , NOTE TEXT, COMPLETED INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertTask(Item item) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item.getSubject());
        contentValues.put(COL_3, item.getNote());
        contentValues.put(COL_4, item.isCompleted());
        database.insert(TABLE_NAME, null, contentValues);
    }

    public void updateTask(int ID, String subject, String note) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, subject);
        contentValues.put(COL_3, note);
        database.update(TABLE_NAME, contentValues, "ID=?", new String[]{String.valueOf(ID)});
    }

    public void updateCompleted(int ID, int completed) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4, completed);
        database.update(TABLE_NAME, contentValues, "ID=?", new String[]{String.valueOf(ID)});
    }

    public void deleteTask(Item item) {
        database = this.getWritableDatabase();
        database.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(item.getItemID())});
    }

    @SuppressLint("Range")
    public List<Item> getAllTasks() {

        database = this.getWritableDatabase();
        Cursor cursor = null;
        List<Item> taskList = new ArrayList<>();

        database.beginTransaction();
        try {
            cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null ) {
                if (cursor.moveToFirst()) {
                    do {
                        Item item = new Item();
                        item.setItemID(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        item.setSubject(cursor.getString(cursor.getColumnIndex(COL_2)));
                        item.setNote(cursor.getString(cursor.getColumnIndex(COL_3)));
                        item.setCompleted(cursor.getInt(cursor.getColumnIndex(COL_4))==1);
                        taskList.add(item);
                    } while (cursor.moveToNext());
                }
            }
        }  finally {
            database.endTransaction();
            cursor.close();
        }
        return taskList;
    }
}
