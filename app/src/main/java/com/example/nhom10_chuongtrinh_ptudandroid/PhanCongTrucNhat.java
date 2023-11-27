package com.example.nhom10_chuongtrinh_ptudandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nhom10_chuongtrinh_ptudandroid.Adapter.PhanCongAdapter;
import com.example.nhom10_chuongtrinh_ptudandroid.Database.DangKyThucHanhHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Database.GiaoVienHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Database.PhanCongHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Database.SinhVienHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.PhanCong;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.SinhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class PhanCongTrucNhat extends AppCompatActivity {
    EditText editMSV, editTenSV, editNote, editCa;
    Button btnThem;
    RecyclerView tbphancong;
    PhanCongAdapter adapter;
    List<PhanCong> phanCongList = new ArrayList<>();
    List<SinhVien> sinhVienList = new ArrayList<>();
    PhanCongHelper pch;
    SinhVienHelper svh;
    DangKyThucHanhHelper dkh;
    String username;
    ArrayList<String> tenloplist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phan_cong_truc_nhat);
        getFormWidget();
        autoSelect();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonEvent();
            }
        });

        setRecycleView();
    }

    public void getFormWidget() {
        editMSV = findViewById(R.id.editMSV);
        editTenSV = findViewById(R.id.editTenSV);
        editNote = findViewById(R.id.editNote);
        editCa = findViewById(R.id.editCa);
        btnThem = findViewById(R.id.btnThem);
        tbphancong = findViewById(R.id.tbphancong);
        pch = new PhanCongHelper(getApplicationContext());
        svh = new SinhVienHelper(getApplicationContext());
        dkh = new DangKyThucHanhHelper(getApplicationContext());
        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("username");
        }
        tenloplist = dkh.getColLop();

        if(phanCongList.isEmpty())
            phanCongList = pch.getAll();
    }

    private void setRecycleView() {
        tbphancong.setHasFixedSize(true);
        tbphancong.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PhanCongAdapter(this, phanCongList);
        tbphancong.setAdapter(adapter);
    }

    public void autoSelect() {
        Hashtable<String, Integer> songay = dkh.soNgayThucHanh();
        for (String tenlop : tenloplist) {
            if (!songay.isEmpty()) {
                List<SinhVien> sinhVienList = svh.autoSelect((songay.get(tenlop) * 4) - 3, songay.get(tenlop) * 4, tenlop);
                String ca = dkh.getColCa(tenlop, songay.get(tenlop));
                String ngay = dkh.getColNgay(tenlop, songay.get(tenlop));

                List<PhanCong> newPhanCongList = new ArrayList<>();

                for (SinhVien sinhVien : sinhVienList) {
                    String ten = sinhVien.getTen();
                    String masv = sinhVien.getMsv();
                    newPhanCongList.add(new PhanCong(masv, ten, "Đến ngày trực nhật", tenlop, ca, ngay));
                }
                pch.addRecord(newPhanCongList);
            } else {
                Toast.makeText(this, "Không có lịch thực hành", Toast.LENGTH_SHORT).show();
            }
        }
        phanCongList = pch.getAll();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void ButtonEvent() {
        String msv = editMSV.getText().toString();
        String ten = editTenSV.getText().toString();
        String note = editNote.getText().toString();
        String ca = editCa.getText().toString();

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String tenlop = svh.getColLop(msv);
        if (!msv.isEmpty() && !ten.isEmpty() && !note.isEmpty() && !ca.isEmpty()) {
            phanCongList.add(new PhanCong(msv, ten, note, tenlop, ca, dateFormat.format(currentDate)));
            pch.addRecord(phanCongList);
        } else {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
    }
}
