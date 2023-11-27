package com.example.nhom10_chuongtrinh_ptudandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.nhom10_chuongtrinh_ptudandroid.Database.GiaoVienHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Database.SinhVienHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.GiaoVien;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.SinhVien;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginMainActivity extends AppCompatActivity {
    EditText userName, password;
    RadioGroup rdGroup;
    RadioButton rdGv, rdSv;
    Button btnLogin;
    GiaoVienHelper gvh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main_activity);
        getFormWidget();
        fakeData();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtuser = userName.getText().toString(),
                        txtpass = password.getText().toString();
                if(rdGv.isChecked()){
                    if(gvh.check(txtuser, txtpass)){
                        Intent intent = new  Intent(LoginMainActivity.this, ManHinhGiaoVien.class);
                        intent.putExtra("username", txtuser);
                        intent.putExtra("tenlop", gvh.getColTenLop(txtuser));
                        startActivity(intent);
                    } else {
                        //Thong bao
                    }
                } else if (rdSv.isChecked()){
                    //Chua code
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
    }
    public void fakeData() {
        List<GiaoVien> giaoVienList = new ArrayList<>();
        giaoVienList.add(new GiaoVien("a",""));
        giaoVienList.add(new GiaoVien("b",""));
        gvh.addRecord(giaoVienList);
    }
}