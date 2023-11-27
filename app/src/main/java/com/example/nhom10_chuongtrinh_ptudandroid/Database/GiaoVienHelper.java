package com.example.nhom10_chuongtrinh_ptudandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nhom10_chuongtrinh_ptudandroid.Tables.GiaoVien;

import java.util.ArrayList;
import java.util.List;

public class GiaoVienHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BTL.db";
    private static final String TABLE_NAME = "giaovien";
    private static final String COL_TEN_LOP = "tenLop";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final int DATABASE_VERSION = 6;

    public GiaoVienHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatement = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT, %s TEXT)",
                TABLE_NAME, COL_USERNAME, COL_PASSWORD, COL_TEN_LOP);
        db.execSQL(createStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String deleteStatement = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(deleteStatement);
        onCreate(db);
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }
    public void addRecord(List<GiaoVien> list) {
        SQLiteDatabase db = getWritableDatabase();
        for (GiaoVien c : list) {
            ContentValues values = new ContentValues();

            values.put(COL_USERNAME, c.getUsername());
            values.put(COL_PASSWORD, c.getPassword());

            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    public String selectTenLopWhere(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String statement = "SELECT " + COL_TEN_LOP + " FROM " + TABLE_NAME + " WHERE " + COL_USERNAME + " = ?";

        Cursor cursor = db.rawQuery(statement, new String[]{username});

        String tenLop = null;
        if (cursor != null && cursor.moveToFirst()) {
            tenLop = cursor.getString(0);
            cursor.close();
        }
        db.close();

        return tenLop;
    }
    public boolean check(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String statement = "SELECT COUNT(*)" + " FROM " + TABLE_NAME +
                        " WHERE " + COL_USERNAME + " = ? AND " +
                                    COL_PASSWORD + " = ?";

        Cursor cursor = db.rawQuery(statement, new String[]{username, password});

        int result = 0;
        if (cursor != null && cursor.moveToFirst()) {
            result = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        if(result == 0)
            return false;
        else
            return true;
    }

    public String getColTenLop(String username){
        SQLiteDatabase db = getReadableDatabase();
        String statement = "SELECT " + COL_TEN_LOP + " FROM " + TABLE_NAME +
                " WHERE " + COL_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(statement, new String[]{username});

        String result = null;
        if (cursor != null && cursor.moveToFirst()) {
            result = cursor.getString(0);
            cursor.close();
        }
        db.close();
        return result;
    }

    public List<GiaoVien> getAll() {
        SQLiteDatabase db = getReadableDatabase();
        List<GiaoVien> list = new ArrayList<>();

        String statement = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(statement, null);
        if(cursor != null) {
            cursor.moveToFirst();
            do{
                list.add(new GiaoVien(cursor.getString(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }
}
