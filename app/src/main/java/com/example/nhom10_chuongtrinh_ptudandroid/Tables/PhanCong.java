package com.example.nhom10_chuongtrinh_ptudandroid.Tables;

public class PhanCong {
    private String masv, ten, note, tenLopDK, ca, ngay;

    public PhanCong(String masv, String ten, String note, String tenLopDK, String ca, String ngay) {
        this.masv = masv;
        this.ten = ten;
        this.note = note;
        this.tenLopDK = tenLopDK;
        this.ca = ca;
        this.ngay = ngay;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTenLopDK() {
        return tenLopDK;
    }

    public void setTenLopDK(String tenLopDK) {
        this.tenLopDK = tenLopDK;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
