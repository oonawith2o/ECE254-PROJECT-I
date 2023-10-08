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

public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    private static final String DATABASE_NAME = "DOIT_DATABASE";
    private static final String TABLE_NAME = "ITEMS_TABLE";
    private static final String[] ITEMS_COLUMNS = {"ID", "SUBJECT", "NOTES", "COMPLETED", "ITEM_CREATION_TIME_DATE", "ITEM_DUE_DATE_TIME", "PRIORITY", "PARTICIPANTS"};



    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, SUBJECT TEXT, NOTES TEXT, COMPLETED INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertTask(Item item) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEMS_COLUMNS[0], item.getItemID());
        contentValues.put(ITEMS_COLUMNS[1], item.getSubject());
        contentValues.put(ITEMS_COLUMNS[2], item.getNote());
        contentValues.put(ITEMS_COLUMNS[3], item.isCompleted());
        database.insert(TABLE_NAME, null, contentValues);
    }

    public void updateTask(int ID, String subject, String note) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEMS_COLUMNS[1], subject);
        contentValues.put(ITEMS_COLUMNS[2], note);
        database.update(TABLE_NAME, contentValues, "ID=?", new String[]{String.valueOf(ID)});
    }

    public void deleteTask(Item item) {
        database = this.getWritableDatabase();
        database.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(item.getItemID())});
    }

    @SuppressLint("Range")
    public ArrayList<Item> getAllTasks() {
        database = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<Item> taskList = new ArrayList<>();

        database.beginTransaction();
        try {
            cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null ) {
                if (cursor.moveToFirst()) {
                    do {
                        Item item = new Item();
                        item.setAll(cursor.getInt(cursor.getColumnIndex(ITEMS_COLUMNS[0])), cursor.getString(cursor.getColumnIndex(ITEMS_COLUMNS[1])), cursor.getString(cursor.getColumnIndex(ITEMS_COLUMNS[2])), (1 == cursor.getInt(cursor.getColumnIndex(ITEMS_COLUMNS[3]))), null, null, -1, null);
                        taskList.add(item);
                    } while (cursor.moveToNext());
                }
            }
        }  finally {
            database.endTransaction();
            assert cursor != null;
            cursor.close();
        }

        return taskList;
    }
}
