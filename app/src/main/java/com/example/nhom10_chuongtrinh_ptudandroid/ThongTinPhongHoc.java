package com.example.nhom10_chuongtrinh_ptudandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThongTinPhongHoc extends AppCompatActivity {
    Button btnDangKyLop, btnPhanCong, btnThongKe;
    String tenPhong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thong_tin_phong_hoc);
        getFormWidget();
        addEvent();
    }
    public void getFormWidget(){
        btnDangKyLop = findViewById(R.id.btnDangKyLop);
        btnPhanCong = findViewById(R.id.btnPhanCong);
        btnThongKe = findViewById(R.id.btnThongKe);
        Intent intent = getIntent();
        tenPhong = intent.getStringExtra("tenPhong");
    }
    protected class MyButtonEvent implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnDangKyLop) {
                Intent intent = new Intent(ThongTinPhongHoc.this, DangKyLopHoc.class);
                intent.putExtra("tenPhong", tenPhong);
                startActivity(intent);
            } else if (v.getId() == R.id.btnPhanCong) {
                Intent intent = new Intent(ThongTinPhongHoc.this, PhanCongTrucNhat.class);
                startActivity(intent);
            } else if (v.getId() == R.id.btnThongKe){
                Intent intent = new Intent(ThongTinPhongHoc.this, BaoCaoThongKe.class);
                startActivity(intent);
            }
        }
    }
    public void addEvent(){
        btnDangKyLop.setOnClickListener(new MyButtonEvent());
        btnPhanCong.setOnClickListener(new MyButtonEvent());
        btnThongKe.setOnClickListener(new MyButtonEvent());
    }
}
