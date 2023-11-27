package com.example.nhom10_chuongtrinh_ptudandroid.Tables;

public class SinhVien {
    private String msv, ten, lop;
    private int stt;

    public SinhVien(String msv, String ten, String lop, int stt) {
        this.msv = msv;
        this.ten = ten;
        this.lop = lop;
        this.stt = stt;
    }

    public String getMsv() {
        return msv;
    }

    public void setMsv(String msv) {
        this.msv = msv;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }
}
