package com.example.nhom10_chuongtrinh_ptudandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PhanCongTrucNhat extends AppCompatActivity{
    EditText editMSV, editTenSV, editNote;
    Button btnThem, btnLuuBang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phan_cong_truc_nhat);
        editMSV = findViewById(R.id.editMSV);
        editTenSV = findViewById(R.id.editTenSV);
        editNote = findViewById(R.id.editNote);
        btnThem = findViewById(R.id.btnThem);
        btnLuuBang = findViewById(R.id.btnLuuBang);
    }
}
