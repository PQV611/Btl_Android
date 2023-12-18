package com.example.nhom10_chuongtrinh_ptudandroid.Tables;

public class PhongHoc {
    private String tenPhong, thietBiHuHai, thietBiThieu, ngay, ca, masv;

    public PhongHoc(String tenPhong, String thietBiHuHai, String thietBiThieu, String ngay, String ca, String masv) {
        this.tenPhong = tenPhong;
        this.thietBiHuHai = thietBiHuHai;
        this.thietBiThieu = thietBiThieu;
        this.ngay = ngay;
        this.ca = ca;
        this.masv = masv;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getThietBiHuHai() {
        return thietBiHuHai;
    }

    public void setThietBiHuHai(String thietBiHuHai) {
        this.thietBiHuHai = thietBiHuHai;
    }

    public String getThietBiThieu() {
        return thietBiThieu;
    }

    public void setThietBiThieu(String thietBiThieu) {
        this.thietBiThieu = thietBiThieu;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }
}
