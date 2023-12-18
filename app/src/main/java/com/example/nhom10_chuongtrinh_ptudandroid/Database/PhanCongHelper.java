package com.example.nhom10_chuongtrinh_ptudandroid.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nhom10_chuongtrinh_ptudandroid.Tables.PhanCong;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PhanCongHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BTL.db";
    private static final String TABLE_NAME = "phancong";
    private static final String COL_MSV = "masv";
    private static final String COL_TEN = "ten";
    private static final String COL_NOTE = "note";
    private static final String COL_TEN_LOP_DK = "tenLopDK";
    private static final String COL_CA = "ca";
    private static final String COL_NGAY = "ngay";
    private static final int DATABASE_VERSION = 1;

    public PhanCongHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.e("Database", "onCreate called");
            String createStatement = String.format("CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, PRIMARY KEY(%s, %s, %s, %s))",
                    TABLE_NAME, COL_MSV, COL_TEN, COL_NOTE, COL_TEN_LOP_DK, COL_CA, COL_NGAY,
                    COL_MSV, COL_TEN_LOP_DK, COL_CA, COL_NGAY);
            db.execSQL(createStatement);
        } catch (Exception e) {
            Log.e("PhanCongHelper", "Error creating database", e);
        }
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
    public void addRecord(List<PhanCong> list) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (PhanCong c : list) {
                ContentValues values = new ContentValues();
                values.put(COL_MSV, c.getMasv());
                values.put(COL_TEN, c.getTen());
                values.put(COL_NOTE, c.getNote());
                values.put(COL_TEN_LOP_DK, c.getTenLopDK());
                values.put(COL_CA, c.getCa());
                values.put(COL_NGAY, c.getNgay());
                db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public boolean getSinhVienTrucNhat(String masv){
        SQLiteDatabase db = getReadableDatabase();
        String statement = "SELECT COUNT(*) FROM " + TABLE_NAME +
                " WHERE DATETIME(" + COL_NGAY + ") >= DATETIME('now') - 1 AND " + COL_MSV + " = ?";

        try {
            Cursor cursor = db.rawQuery(statement, new String[]{masv});

            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                cursor.close();
                return count != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @SuppressLint("Range")
    public List<PhanCong> getAll() {
        SQLiteDatabase db = getReadableDatabase();
        List<PhanCong> list = new ArrayList<>();

        String statement = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(statement, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(new PhanCong(
                        cursor.getString(cursor.getColumnIndex(COL_MSV)),
                        cursor.getString(cursor.getColumnIndex(COL_TEN)),
                        cursor.getString(cursor.getColumnIndex(COL_NOTE)),
                        cursor.getString(cursor.getColumnIndex(COL_TEN_LOP_DK)),
                        cursor.getString(cursor.getColumnIndex(COL_CA)),
                        cursor.getString(cursor.getColumnIndex(COL_NGAY))
                ));
            }
            cursor.close();
        }
        return list;
    }

    @SuppressLint("Range")
    public ArrayList<List<String>> getNgayCaOf(String masv){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<List<String>> list = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        String statement = "SELECT " + COL_NGAY + ", " + COL_CA + " FROM " + TABLE_NAME +
                            " WHERE " + COL_MSV + " = ?";
        Cursor cursor = db.rawQuery(statement, new String[]{masv});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                temp.clear();
                temp.add(cursor.getString(0));
                temp.add(cursor.getString(1));
                list.add(temp);
            }
            cursor.close();
        }
        return list;
    }
}

