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
    private static final int DATABASE_VERSION = 1;

    public DangKyThucHanhHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatement = String.format("CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT, %s TEXT, PRIMARY KEY (%s, %s))",
                TABLE_NAME, COL_TEN_LOP_DK, COL_CA, COL_NGAY, COL_TEN_PHONG,
                COL_CA, COL_NGAY);

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

            db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
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
    public String getColPhong(String ca, String ngay) {
        SQLiteDatabase db = getReadableDatabase();
        String result = null;
        Cursor cursor = null;
        try {
            String statement = "SELECT " + COL_TEN_PHONG + " FROM " + TABLE_NAME +
                    " WHERE " +
                    COL_NGAY + " = ? AND " +
                    COL_CA + " = ?";
            cursor = db.rawQuery(statement, new String[]{ngay, ca});

            while (cursor.moveToNext()) {
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
    private String getSingleColumnValue(String columnName) {
        SQLiteDatabase db = getReadableDatabase();
        String result = null;
        Cursor cursor = null;

        try {
            String statement = "SELECT " + columnName +
                    " FROM " + TABLE_NAME +
                    " ORDER BY DATE(" + COL_NGAY + ") DESC LIMIT 1";

            cursor = db.rawQuery(statement, new String[]{});
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
    private String getSingleColumnValueWhere(String tenlop, String columnName) {
        SQLiteDatabase db = getReadableDatabase();
        String result = null;
        Cursor cursor = null;

        try {
            String statement = "SELECT " + columnName +
                    " FROM " + TABLE_NAME +
                    " WHERE " + COL_TEN_LOP_DK + " = ?" +
                    " ORDER BY DATE(" + COL_NGAY + ") DESC, " + COL_CA + " ASC LIMIT 1";

            cursor = db.rawQuery(statement, new String[]{tenlop});
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
    public boolean check(String ca, String ngay){
        SQLiteDatabase db = getReadableDatabase();
        int result = 0;
        Cursor cursor = null;

        try {
            String statement = "SELECT COUNT(*)" + " FROM " + TABLE_NAME + " WHERE " + COL_CA + " = ? AND " + COL_NGAY + " = ?";

            cursor = db.rawQuery(statement, new String[]{ca, ngay});
            if (cursor.moveToFirst()) {
                result = cursor.getInt(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        if (result == 0)
            return false;
        else
            return true;
    }
    public String getColCaWhere(String tenlop) {
        return getSingleColumnValueWhere(tenlop, COL_CA);
    }
    public String getColNgayWhere(String tenlop) { return getSingleColumnValueWhere(tenlop, COL_NGAY); }
    public String getColCa() {
        return getSingleColumnValue(COL_CA);
    }
    public String getColNgay() { return getSingleColumnValue(COL_NGAY); }

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