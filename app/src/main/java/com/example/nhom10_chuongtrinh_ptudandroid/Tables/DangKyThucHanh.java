package com.example.nhom10_chuongtrinh_ptudandroid.Tables;

public class DangKyThucHanh {
    private String tenLopDK, ca, ngay, tenPhong;

    public DangKyThucHanh(String tenLopDK, String ca, String ngay, String tenPhong) {
        this.tenLopDK = tenLopDK;
        this.ca = ca;
        this.ngay = ngay;
        this.tenPhong = tenPhong;
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

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }
}
