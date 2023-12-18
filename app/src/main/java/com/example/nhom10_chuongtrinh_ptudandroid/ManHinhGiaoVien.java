package com.example.nhom10_chuongtrinh_ptudandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManHinhGiaoVien extends AppCompatActivity {
    Button btnP1,btnP2, btnP3, btnP4, btnLogOut;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_giao_vien);
        getFormWidget();
        addEvent();
    }
    public void getFormWidget(){
        btnP1 = findViewById(R.id.btnP1);
        btnP2 = findViewById(R.id.btnP2);
        btnP3 = findViewById(R.id.btnP3);
        btnP4 = findViewById(R.id.btnP4);
        btnLogOut = findViewById(R.id.btnLogOut);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }
    protected class MyButtonEvent implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnP1){
                Intent intent = new Intent(ManHinhGiaoVien.this, ThongTinPhongHoc.class);
                intent.putExtra("username", username);
                intent.putExtra("tenPhong", "Phòng 1");
                startActivity(intent);
            } else if(v.getId() == R.id.btnP2){
                Intent intent = new Intent(ManHinhGiaoVien.this, ThongTinPhongHoc.class);
                intent.putExtra("username", username);
                intent.putExtra("tenPhong", "Phòng 2");
                startActivity(intent);
            } else if(v.getId() == R.id.btnP3){
                Intent intent = new Intent(ManHinhGiaoVien.this, ThongTinPhongHoc.class);
                intent.putExtra("username", username);
                intent.putExtra("tenPhong", "Phòng 3");
                startActivity(intent);
            } else if(v.getId() == R.id.btnP4){
                Intent intent = new Intent(ManHinhGiaoVien.this, ThongTinPhongHoc.class);
                intent.putExtra("username", username);
                intent.putExtra("tenPhong", "Phòng 4");
                startActivity(intent);
            } else if (v.getId() == R.id.btnLogOut){
                finish();
            }
        }
    }
    public void addEvent(){
        btnP1.setOnClickListener(new MyButtonEvent());
        btnP2.setOnClickListener(new MyButtonEvent());
        btnP3.setOnClickListener(new MyButtonEvent());
        btnP4.setOnClickListener(new MyButtonEvent());
        btnLogOut.setOnClickListener(new MyButtonEvent());
    }
}
