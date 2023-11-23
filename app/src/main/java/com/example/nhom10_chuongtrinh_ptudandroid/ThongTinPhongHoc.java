package com.example.nhom10_chuongtrinh_ptudandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThongTinPhongHoc extends AppCompatActivity {
    Button btnDangKyLop, btnPhanCong, btnThongKe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thong_tin_phong_hoc);
        btnDangKyLop = findViewById(R.id.btnDangKyLop);
        btnPhanCong = findViewById(R.id.btnPhanCong);
        btnThongKe = findViewById(R.id.btnThongKe);

        btnDangKyLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongTinPhongHoc.this, DangKyLopHoc.class);
                startActivity(intent);
            }
        });

        btnPhanCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongTinPhongHoc.this, PhanCongTrucNhat.class);
                startActivity(intent);
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongTinPhongHoc.this, BaoCaoThongKe.class);
                startActivity(intent);
            }
        });

    }
}
