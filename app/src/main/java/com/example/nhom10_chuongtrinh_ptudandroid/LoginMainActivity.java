package com.example.nhom10_chuongtrinh_ptudandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nhom10_chuongtrinh_ptudandroid.Database.GiaoVienHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Database.PhanCongHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Database.SinhVienHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.GiaoVien;

import java.util.ArrayList;
import java.util.List;

public class LoginMainActivity extends AppCompatActivity {
    EditText userName, password;
    RadioGroup rdGroup;
    RadioButton rdGv, rdSv;
    Button btnLogin;
    GiaoVienHelper gvh;
    SinhVienHelper svh;
    PhanCongHelper pch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main_activity);
        getFormWidget();
//        fakeData();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtuser = userName.getText().toString(),
                        txtpass = password.getText().toString();
                if(rdGv.isChecked()){
                    if(gvh.check(txtuser, txtpass)){
                        Intent intent = new  Intent(LoginMainActivity.this, ManHinhGiaoVien.class);
                        intent.putExtra("username", txtuser);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Không đúng thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                    }
                } else if (rdSv.isChecked()){
                    if(svh.check(txtuser)) {
                        if (svh != null && pch != null) {
                            Intent intent = new Intent(LoginMainActivity.this, DiemDanhSinhVienTrucNhatActivity.class);
                            intent.putExtra("username", txtuser);
                            intent.putExtra("source", "ActivityLogin");
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Không đúng thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Chưa chọn vị trí", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void getFormWidget(){
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        rdGroup = findViewById(R.id.rdGroup);
        btnLogin = findViewById(R.id.btnLogin);
        rdGv = findViewById(R.id.rdGv);
        rdSv = findViewById(R.id.rdSv);
        gvh = new GiaoVienHelper(getApplicationContext());
        svh = new SinhVienHelper(getApplicationContext());
        pch = new PhanCongHelper(getApplicationContext());
    }
    public void fakeData() {
        try {
            if (!gvh.check("a")){
                List<GiaoVien> giaoVienList = new ArrayList<>();
                giaoVienList.add(new GiaoVien("giaovien1",""));
                giaoVienList.add(new GiaoVien("giaovien2",""));
                gvh.addRecord(giaoVienList);
                gvh.importCsvData(gvh.getWritableDatabase(), getApplicationContext());
            }
        } catch (Exception e){
            Log.d("announcement", "Đã fake data trước đó");
        }
    }
}
