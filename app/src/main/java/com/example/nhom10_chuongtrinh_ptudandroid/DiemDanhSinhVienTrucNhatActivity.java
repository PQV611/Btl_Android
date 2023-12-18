package com.example.nhom10_chuongtrinh_ptudandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.nhom10_chuongtrinh_ptudandroid.Adapter.PhanCongAdapter;
import com.example.nhom10_chuongtrinh_ptudandroid.Adapter.ThongTinTrucNhatAdapter;
import com.example.nhom10_chuongtrinh_ptudandroid.Database.DangKyThucHanhHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Database.PhanCongHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Database.SinhVienHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.DangKyThucHanh;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.PhanCong;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DiemDanhSinhVienTrucNhatActivity extends AppCompatActivity {
    EditText editTBHu, editTBThieu;
    TextView tvTen;
    Button btnDangXuat;
    PhanCongHelper pch;
    DangKyThucHanhHelper dkh;
    SinhVienHelper svh;
    RecyclerView tbThongTinTrucNhat;
    ThongTinTrucNhatAdapter adapter;
    ArrayList<List<String>> thongTinTrucNhatList = new ArrayList<>();
    static String masv, tenLopDK, tenSV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diem_danh_sinh_vien_truc_nhat);
        getFormWidget();
        showTask();
        setRecycleView();
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginMainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void getFormWidget(){
        editTBHu = findViewById(R.id.editTBHu);
        editTBThieu = findViewById(R.id.editTBThieu);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        tbThongTinTrucNhat = findViewById(R.id.tbthongtintrucnhat);
        tvTen = findViewById(R.id.textView16);
        adapter = new ThongTinTrucNhatAdapter(this, thongTinTrucNhatList);
        pch = new PhanCongHelper(getApplicationContext());
        dkh = new DangKyThucHanhHelper(getApplicationContext());
        svh = new SinhVienHelper(getApplicationContext());
        Intent intent = getIntent();
        String source = intent.getStringExtra("source");
        if (source.compareTo("ActivityLogin") == 0){
            masv = intent.getStringExtra("username");
            tenLopDK = svh.getColLop(masv);
        }
        tenSV = svh.getColTen(masv);
        tvTen.setText(tenSV);
    }
    public void setRecycleView() {
        tbThongTinTrucNhat.setHasFixedSize(true);
        tbThongTinTrucNhat.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ThongTinTrucNhatAdapter(this, thongTinTrucNhatList);
        tbThongTinTrucNhat.setAdapter(adapter);
    }
    private void showTask() {
        thongTinTrucNhatList.clear();
        ArrayList<List<String>> tasks = pch.getNgayCaOf(masv);
        for (List<String> t : tasks){
            List<String> task = new ArrayList<>();
            String ca = t.get(1),
                    ngay = t.get(0),
                    tenPhong = dkh.getColPhong(ca, ngay, tenLopDK);
            task.add(tenPhong);
            task.add(ngay);
            task.add(ca);
            task.add(masv);
            thongTinTrucNhatList.add(task);
        }
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }
}