package com.example.nhom10_chuongtrinh_ptudandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nhom10_chuongtrinh_ptudandroid.Tables.PhongHoc;

import java.util.ArrayList;
import java.util.List;

public class PhongHocHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BTL.db";
    private static final String TABLE_NAME = "phonghoc";
    private static final String COL_TEN_PHONG = "tenPhong";
    private static final String COL_THIET_BI_HU_HAI = "thietBiHuHai";
    private static final String COL_THIET_BI_THIEU = "thietBiThieu";
    private static final int DATABASE_VERSION = 1;

    public PhongHocHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatement = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT, %s TEXT)",
                TABLE_NAME, COL_TEN_PHONG, COL_THIET_BI_HU_HAI, COL_THIET_BI_THIEU);
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
    public void addRecord(List<PhongHoc> list) {
        SQLiteDatabase db = getWritableDatabase();
        for(PhongHoc c : list) {
            ContentValues values = new ContentValues();

            values.put(COL_TEN_PHONG, c.getTenPhong());
            values.put(COL_THIET_BI_HU_HAI, c.getThietBiHuHai());
            values.put(COL_THIET_BI_THIEU, c.getThietBiThieu());
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }


    public List<PhongHoc> getAll() {
        SQLiteDatabase db = getReadableDatabase();
        List<PhongHoc> list = new ArrayList<>();

        String statement = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(statement, null);
        if(cursor != null) {
            cursor.moveToFirst();
            do{
                list.add(new PhongHoc(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        return list;
    }
}
