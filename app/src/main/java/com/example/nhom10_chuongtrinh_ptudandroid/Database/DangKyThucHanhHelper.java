package com.example.nhom10_chuongtrinh_ptudandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nhom10_chuongtrinh_ptudandroid.Tables.DangKyThucHanh;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class DangKyThucHanhHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BTL.db";
    private static final String TABLE_NAME = "dangkythuchanh";
    private static final String COL_TEN_LOP_DK = "tenLopDK";
    private static final String COL_CA = "ca";
    private static final String COL_NGAY = "ngay";
    private static final String COL_TEN_PHONG = "tenPhong";
    private static final int DATABASE_VERSION = 6;

    public DangKyThucHanhHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatement = String.format("CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_NAME, COL_TEN_LOP_DK, COL_CA, COL_NGAY, COL_TEN_PHONG);
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

    public void addRecord(List<DangKyThucHanh> list) {
        SQLiteDatabase db = getWritableDatabase();
        for (DangKyThucHanh c : list) {
            ContentValues values = new ContentValues();

            values.put(COL_TEN_LOP_DK, c.getTenLopDK());
            values.put(COL_CA, c.getCa());
            values.put(COL_NGAY, c.getNgay());
            values.put(COL_TEN_PHONG, c.getTenPhong());
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }

    public Hashtable<String, Integer> soNgayThucHanh() {
        SQLiteDatabase db = getReadableDatabase();
        Hashtable<String, Integer> hashtable = new Hashtable<>();

        String query = "SELECT " + COL_TEN_LOP_DK + ", COUNT(*) FROM " + TABLE_NAME +
                " GROUP BY " + COL_TEN_LOP_DK;

        Cursor cursor = db.rawQuery(query, new String[]{});

        while (cursor.moveToNext()) {
            String key = cursor.getString(0);
            int value = cursor.getInt(1);
            hashtable.put(key, value);
        }

        cursor.close();
        db.close();
        return hashtable;
    }

    public ArrayList<String> getColLop() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> result = new ArrayList<>();
        Cursor cursor = null;
        try {
            String statement = "SELECT DISTINCT " + COL_TEN_LOP_DK + " FROM " + TABLE_NAME;
            cursor = db.rawQuery(statement, null);

            while (cursor.moveToNext()) {
                result.add(cursor.getString(0));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return result;
    }


    private String getSingleColumnValue(String tenlop, int songay, String columnName) {
        SQLiteDatabase db = getReadableDatabase();
        String result = null;
        Cursor cursor = null;

        try {
            String subquery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_TEN_LOP_DK + " = ?";
            String statement = "SELECT " + columnName + " FROM (" + subquery +
                    ") ORDER BY " + COL_NGAY + " ASC LIMIT 1 OFFSET ?";

            cursor = db.rawQuery(statement, new String[]{tenlop, String.valueOf(songay - 1)});
            if (cursor.moveToFirst()) {
                result = cursor.getString(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return result;
    }

    public String getColCa(String tenlop, int songay) {
        return getSingleColumnValue(tenlop, songay, COL_CA);
    }

    public String getColNgay(String tenlop, int songay) {
        return getSingleColumnValue(tenlop, songay, COL_NGAY);
    }


    public List<DangKyThucHanh> getAll() {
        SQLiteDatabase db = getReadableDatabase();
        List<DangKyThucHanh> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            String statement = "SELECT * FROM " + TABLE_NAME;
            cursor = db.rawQuery(statement, null);
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    list.add(new DangKyThucHanh(cursor.getString(0), cursor.getString(1),
                            cursor.getString(2), cursor.getString(3)));
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