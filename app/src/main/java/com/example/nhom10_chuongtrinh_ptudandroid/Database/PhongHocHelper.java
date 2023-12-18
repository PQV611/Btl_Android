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
    private static final String COL_CA = "ca";
    private static final String COL_NGAY = "ngay";
    private static final String COL_MASV = "masv";
    private static final int DATABASE_VERSION = 1;

    public PhongHocHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatement = String.format("CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, PRIMARY KEY (%s, %s, %s, %s))",
                TABLE_NAME, COL_TEN_PHONG, COL_THIET_BI_HU_HAI, COL_THIET_BI_THIEU, COL_CA, COL_NGAY, COL_MASV,
                            COL_TEN_PHONG, COL_NGAY, COL_CA, COL_MASV);
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
    public void addRecord(PhongHoc phongHoc) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TEN_PHONG, phongHoc.getTenPhong());
        values.put(COL_THIET_BI_HU_HAI, phongHoc.getThietBiHuHai());
        values.put(COL_THIET_BI_THIEU, phongHoc.getThietBiThieu());
        values.put(COL_CA, phongHoc.getCa());
        values.put(COL_NGAY, phongHoc.getNgay());
        values.put(COL_MASV, phongHoc.getMasv());
        db.insert(TABLE_NAME, null, values);
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
                list.add(new PhongHoc(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(4), cursor.getString(3), cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean checkExist(String masv, String tenphong, String ca, String ngay){
        SQLiteDatabase db = getReadableDatabase();
        String statement = "SELECT COUNT(*) FROM " + TABLE_NAME +
                " WHERE " + COL_MASV + " = ?" +
                " AND " + COL_TEN_PHONG + " = ?" +
                " AND " + COL_CA + " = ?" +
                " AND " + COL_NGAY + " = ?";
        Cursor cursor = db.rawQuery(statement, new String[]{masv, tenphong, ca, ngay});
        if(cursor != null) {
            while (cursor.moveToNext()) {
                int x = cursor.getInt(0);
                if (x == 0)
                    return true;
                else
                    return false;
            }
        }
        return false;
    }
    public void updateRecord(String thietBiThieu, String thietBiHuHai, String masv, String tenphong, String ca, String ngay) {
        SQLiteDatabase db = getWritableDatabase();  // Use getWritableDatabase instead of getReadableDatabase for updates
        String statement = "UPDATE " + TABLE_NAME +
                " SET " + COL_THIET_BI_THIEU + " = ?," + COL_THIET_BI_HU_HAI + " = ?" +
                " WHERE " + COL_MASV + " = ?" +
                " AND " + COL_TEN_PHONG + " = ?" +
                " AND " + COL_CA + " = ?" +
                " AND " + COL_NGAY + " = ?";

        db.execSQL(statement, new String[]{thietBiThieu, thietBiHuHai, masv, tenphong, ca, ngay});
        db.close();
    }

    public ArrayList<String> ThongKeTheoNgay(String ngay, String tenphong) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> result1 = new ArrayList<>(),
                          result2 = new ArrayList<>();
        ArrayList<String> latest_result = new ArrayList<>();

        // Query for COL_THIET_BI_HU_HAI
        try (Cursor cursor1 = db.rawQuery("SELECT DISTINCT " + COL_THIET_BI_HU_HAI +
                " FROM " + TABLE_NAME +
                " WHERE " + COL_TEN_PHONG + " = ?" +
                " AND " + COL_NGAY + " = ?", new String[]{tenphong, ngay})) {

            while (cursor1.moveToNext()) {
                result1.add(cursor1.getString(0));
            }
        }

        // Query for COL_THIET_BI_THIEU
        try (Cursor cursor2 = db.rawQuery("SELECT DISTINCT " + COL_THIET_BI_THIEU +
                " FROM " + TABLE_NAME +
                " WHERE " + COL_TEN_PHONG + " = ?" +
                " AND " + COL_NGAY + " = ?", new String[]{tenphong, ngay})) {

            while (cursor2.moveToNext()) {
                result2.add(cursor2.getString(0));
            }
        }
        StringBuilder temp = new StringBuilder();
        for (String value : result1)
            temp.append(value).append(", ");
        latest_result.add(temp.toString().trim().trim());

        temp = new StringBuilder();
        for (String value : result2)
            temp.append(value).append(", ");
        latest_result.add(temp.toString().trim().trim());
        return latest_result;
    }
}
