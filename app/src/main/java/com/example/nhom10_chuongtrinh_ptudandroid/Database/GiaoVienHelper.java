package com.example.nhom10_chuongtrinh_ptudandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.example.nhom10_chuongtrinh_ptudandroid.LoginMainActivity;
import com.example.nhom10_chuongtrinh_ptudandroid.R;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.GiaoVien;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GiaoVienHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BTL.db";
    private static final String TABLE_NAME_dkh = "dangkythuchanh";
    private static final String COL_TEN_LOP_DK = "tenLopDK";
    private static final String COL_CA = "ca";
    private static final String COL_NGAY = "ngay";
    private static final String COL_TEN_PHONG = "tenPhong";
    private static final String TABLE_NAME_pch = "phancong";
    private static final String COL_MSV = "masv";
    private static final String COL_TEN = "ten";
    private static final String COL_NOTE = "note";
    private static final String TABLE_NAME_phh = "phonghoc";
    private static final String COL_THIET_BI_HU_HAI = "thietBiHuHai";
    private static final String COL_THIET_BI_THIEU = "thietBiThieu";
    private static final String TABLE_NAME_svh = "sinhvien";
    private static final String COL_STT = "stt";
    private static final String COL_LOP = "lop";
    private static final String TABLE_NAME_gvh = "giaovien";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final int DATABASE_VERSION = 1;

    public GiaoVienHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable1(db);
        createTable2(db);
        createTable3(db);
        createTable4(db);
        createTable5(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String deleteStatement = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME_gvh);
        db.execSQL(deleteStatement);
        onCreate(db);
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_gvh);
        db.close();
    }
    public void addRecord(List<GiaoVien> list) {
        SQLiteDatabase db = getWritableDatabase();
        for (GiaoVien c : list) {
            ContentValues values = new ContentValues();

            values.put(COL_USERNAME, c.getUsername());
            values.put(COL_PASSWORD, c.getPassword());

            db.insert(TABLE_NAME_gvh, null, values);
        }
        db.close();
    }

    public boolean check(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String statement = "SELECT COUNT(*)" + " FROM " + TABLE_NAME_gvh +
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

    public List<GiaoVien> getAll() {
        SQLiteDatabase db = getReadableDatabase();
        List<GiaoVien> list = new ArrayList<>();

        String statement = "SELECT * FROM " + TABLE_NAME_gvh;
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
    public void createTable1(SQLiteDatabase db) {
        String createStatement = String.format("CREATE TABLE %s (%s INTEGER, %s TEXT PRIMARY KEY, %s TEXT, %s TEXT)",
                TABLE_NAME_svh, COL_STT, COL_MSV, COL_TEN, COL_LOP);
        db.execSQL(createStatement);
    }
    public void createTable2(SQLiteDatabase db) {
        String createStatement = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT, %s TEXT)",
                TABLE_NAME_phh, COL_TEN_PHONG, COL_THIET_BI_HU_HAI, COL_THIET_BI_THIEU);
        db.execSQL(createStatement);
    }
    public void createTable3(SQLiteDatabase db) {
        String createStatement = String.format("CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, PRIMARY KEY(%s, %s, %s, %s))",
                TABLE_NAME_pch, COL_MSV, COL_TEN, COL_NOTE, COL_TEN_LOP_DK, COL_CA, COL_NGAY,
                COL_MSV, COL_TEN_LOP_DK, COL_CA, COL_NGAY);
        db.execSQL(createStatement);
    }
    public void createTable4(SQLiteDatabase db) {
        String createStatement = String.format("CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT, %s TEXT, PRIMARY KEY (%s, %s))",
                TABLE_NAME_dkh, COL_TEN_LOP_DK, COL_CA, COL_NGAY, COL_TEN_PHONG,
                COL_CA, COL_NGAY);
        db.execSQL(createStatement);
    }
    public void createTable5(SQLiteDatabase db) {
        String createStatement = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT)",
                TABLE_NAME_gvh, COL_USERNAME, COL_PASSWORD);
        db.execSQL(createStatement);
    }
    public void importCsvData(SQLiteDatabase db, Context context) {
        InputStream inStream = context.getResources().openRawResource(R.raw.sinhvien);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line = "";
        String tableName = TABLE_NAME_svh;
        String columns = "stt, ten, masv, lop";
        String str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
        String str2 = ");";

        db.beginTransaction();
        while (true) {
            try {
                if (!((line = buffer.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            StringBuilder sb = new StringBuilder(str1);
            String[] str = line.split(",");
            sb.append(str[0] + ",");
            sb.append("'" + str[1] + "',");
            sb.append("'" + str[3] + "',");
            sb.append("'" + str[2] + "'");
            sb.append(str2);
            db.execSQL(sb.toString());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
