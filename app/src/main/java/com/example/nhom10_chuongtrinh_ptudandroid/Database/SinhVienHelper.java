package com.example.nhom10_chuongtrinh_ptudandroid.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.SinhVien;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SinhVienHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BTL.db";
    private static final String TABLE_NAME = "sinhvien";
    private static final String COL_STT = "stt";
    private static final String COL_MSV = "masv";
    private static final String COL_LOP = "lop";
    private static final String COL_TEN = "ten";
    private static final int DATABASE_VERSION = 1;

    public SinhVienHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatement = String.format("CREATE TABLE %s (%s INTEGER, %s TEXT PRIMARY KEY, %s TEXT, %s TEXT)",
                TABLE_NAME, COL_STT, COL_MSV, COL_TEN, COL_LOP);
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
    public void addRecord(List<SinhVien> list) {
        SQLiteDatabase db = getWritableDatabase();
        for (SinhVien c : list) {
            ContentValues values = new ContentValues();
            values.put(COL_MSV, c.getMsv());
            values.put(COL_TEN, c.getTen());
            values.put(COL_LOP, c.getLop());
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }
    public String getColLop(String msv){
        SQLiteDatabase db = getReadableDatabase();
        String result = null;
        Cursor cursor = null;
        try {
            String statement = "SELECT DISTINCT " + COL_LOP + " FROM " + TABLE_NAME + " WHERE " + COL_MSV + " = ?";
            cursor = db.rawQuery(statement, new String[]{msv});
            while (cursor.moveToNext()) {
                result = cursor.getString(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }
    @SuppressLint("Range")
    public SinhVien autoSelect(int stt, String tenlop) {
        SQLiteDatabase db = getReadableDatabase();
        SinhVien sv = null;
        String statement = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL_STT + " == ? AND " + COL_LOP + " = ?";
        Cursor cursor = db.rawQuery(statement, new String[]{String.valueOf(stt), tenlop});

        if (cursor != null && cursor.moveToFirst()) {
            sv = new SinhVien(
                    cursor.getString(cursor.getColumnIndex(COL_MSV)),
                    cursor.getString(cursor.getColumnIndex(COL_TEN)),
                    cursor.getString(cursor.getColumnIndex(COL_LOP)),
                    cursor.getInt(cursor.getColumnIndex(COL_STT)));
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return sv;
    }
    public int getAllinClass(String tenlop) {
        SQLiteDatabase db = getReadableDatabase();
        int result = 0;
        String statement = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE " + COL_LOP + " = ?";
        Cursor cursor = db.rawQuery(statement, new String[]{tenlop});
        try {
            if (cursor.moveToFirst()) {
                result = cursor.getInt(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return result;
    }
    public List<SinhVien> getAll() {
        SQLiteDatabase db = getReadableDatabase();
        List<SinhVien> list = new ArrayList<>();

        String statement = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(statement, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new SinhVien(cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getInt(0)));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return list;
    }
}

