package com.example.nhom10_chuongtrinh_ptudandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nhom10_chuongtrinh_ptudandroid.Database.PhongHocHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.PhongHoc;

public class DiemDanh extends AppCompatActivity{
    Button btnDiemDanh;
    EditText edtThietBiThieu, edtThietBiHuHai;
    String tenphong, ngay, ca, masv, thietBiThieu, thietBiHuHai;
    PhongHocHelper phh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diem_danh);
        getFormWidget();
        btnDiemDanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtThietBiHuHai.getText().toString().isEmpty() || edtThietBiThieu.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Thiếu thông tin", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = getIntent();
                    masv = intent.getStringExtra("username");
                    tenphong = intent.getStringExtra("phong");
                    ngay = intent.getStringExtra("ngay");
                    ca = intent.getStringExtra("ca");
                    thietBiThieu = edtThietBiThieu.getText().toString();
                    thietBiHuHai = edtThietBiHuHai.getText().toString();
                    PhongHoc phongHoc = new PhongHoc(tenphong, thietBiHuHai, thietBiThieu, ngay, ca, masv);
                    if (phh.checkExist(masv, tenphong, ca, ngay)){
                        phh.addRecord(phongHoc);
                        Toast.makeText(getApplicationContext(), "Đã thêm thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        phh.updateRecord(thietBiThieu, thietBiHuHai, masv, tenphong, ca, ngay);
                        Toast.makeText(getApplicationContext(), "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
                    }
                    intent = new Intent(getApplicationContext(), DiemDanhSinhVienTrucNhatActivity.class);
                    intent.putExtra("source", "ActivityDiemDanh");
                    startActivity(intent);
                }
            }
        });
    }
    public void getFormWidget(){
        btnDiemDanh = findViewById(R.id.btnDiemDanh);
        edtThietBiHuHai = findViewById(R.id.editTBHu);
        edtThietBiThieu = findViewById(R.id.editTBThieu);
        phh = new PhongHocHelper(getApplicationContext());
    }
}
