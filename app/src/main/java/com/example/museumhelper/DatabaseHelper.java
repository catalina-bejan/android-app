package com.example.museumhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "museum_helper";
    public static final String TABLE_NAME = "visited_museum";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nume TEXT NOT NULL UNIQUE, tara TEXT NOT NULL, oras TEXT NOT NULL, nota TEXT NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(String nume, String tara, String oras, String nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nume", nume);
        contentValues.put("tara", tara);
        contentValues.put("oras", oras);
        contentValues.put("nota", nota);
        System.out.println("A ajuns in insert!");

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date was inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        ArrayList<String> arrayList = new ArrayList<>();
        data.moveToFirst();
        while(!data.isAfterLast()){
            arrayList.add("Muzeul vizitat: " + data.getString(data.getColumnIndex("nume")) + ",tara: " + data.getString(data.getColumnIndex("tara")) + ",orasul: " + data.getString(data.getColumnIndex("oras")) + ",nota: " + data.getString(data.getColumnIndex("nota")));
            data.moveToNext();
        }
        return arrayList;
    }

    public void delete(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " +
                "nume = '" + name + "'";
        db.execSQL(query);
    }

    public void update(String oldName, String nume, String tara, String oras, String nota){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET nume = '" + nume + "', tara = '" + tara + "', oras = '" + oras + "', nota = '" + nota + "' WHERE nume = '" + oldName + "'";
        db.execSQL(query);
    }
}
