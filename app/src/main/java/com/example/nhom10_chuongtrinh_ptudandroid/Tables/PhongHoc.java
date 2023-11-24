package com.example.nhom10_chuongtrinh_ptudandroid.Tables;

public class PhongHoc {
    private String tenPhong, thietBiHuHai, thietBiThieu;

    public PhongHoc(String tenPhong, String thietBiHuHai, String thietBiThieu) {
        this.tenPhong = tenPhong;
        this.thietBiHuHai = thietBiHuHai;
        this.thietBiThieu = thietBiThieu;
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
}
