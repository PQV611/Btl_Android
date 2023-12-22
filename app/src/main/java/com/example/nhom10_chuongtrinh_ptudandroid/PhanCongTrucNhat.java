package com.example.nhom10_chuongtrinh_ptudandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    EditText editMSV;
    RadioGroup radioGroup;
    Button btnThem;
    RecyclerView tbphancong;
    PhanCongAdapter adapter;
    List<PhanCong> phanCongList = new ArrayList<>();
    List<PhanCong> phanCongList1 = new ArrayList<>();
    PhanCongHelper pch;
    SinhVienHelper svh;
    DangKyThucHanhHelper dkh;
    String username;
    ArrayList<String> tenloplist = new ArrayList<>();
    String tenphong;
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
        radioGroup = findViewById(R.id.rdG);
        btnThem = findViewById(R.id.btnThem);
        tbphancong = findViewById(R.id.tbphancong);
        pch = new PhanCongHelper(getApplicationContext());
        svh = new SinhVienHelper(getApplicationContext());
        dkh = new DangKyThucHanhHelper(getApplicationContext());
        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("username");
            tenphong = intent.getStringExtra("tenPhong");
        }
        tenloplist = dkh.getColLop();
    }

    public void setRecycleView() {
        tbphancong.setHasFixedSize(true);
        tbphancong.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PhanCongAdapter(this, phanCongList);
        tbphancong.setAdapter(adapter);
    }

    public void autoSelect() {
        Hashtable<String, Integer> songay = dkh.soNgayThucHanh();
        for (String tenlop : tenloplist) {
            if (!songay.isEmpty()) {
                List<PhanCong> newPhanCongList = new ArrayList<>();
                List<Integer> listAutoSelect = new ArrayList<>();

                for (int i = (songay.get(tenlop) * 4) - 3; i <= (songay.get(tenlop) * 4); i++){
                    if (i != svh.getAllinClass(tenlop))
                        listAutoSelect.add(i % svh.getAllinClass(tenlop));
                    else
                        listAutoSelect.add(i);
                }

                List<SinhVien> sinhVienList = new ArrayList<>();
                for (int stt : listAutoSelect)
                    sinhVienList.add(svh.autoSelect(stt, tenlop));

                String ca = dkh.getColCaWhere(tenlop),
                        ngay = dkh.getColNgayWhere(tenlop);
                for (SinhVien sinhVien : sinhVienList) {
                    String ten = sinhVien.getTen();
                    String masv = sinhVien.getMsv();
                    newPhanCongList.add(new PhanCong(masv, ten, "Đến ngày trực nhật", tenlop, ca, ngay));
                }
                pch.addRecord(newPhanCongList);
            } else
                Toast.makeText(this, "Không có lịch thực hành", Toast.LENGTH_SHORT).show();
        }
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (PhanCong x : pch.getAll()) {
            String ngay = dateFormat.format(currentDate);
            Log.e("abc", ngay);
            if (tenphong.equals(dkh.getColPhong(x.getCa(), x.getNgay(), x.getTenLopDK())))
                phanCongList.add(x);
            else if (pch.getColNote(ngay, x.getMasv()).contains(tenphong))
                phanCongList.add(x);
        }
        for (PhanCong x : phanCongList1)
            if (tenphong.equals(dkh.getColPhong(x.getCa(), x.getNgay(), x.getTenLopDK())))
                phanCongList.add(x);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void ButtonEvent() {
        String msv = editMSV.getText().toString();
        if(svh.check(msv)){
            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            String ca = selectedRadioButton.getText().toString();

            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (!msv.isEmpty() && !ca.isEmpty()) {
                String ten = svh.getColTen(msv);
                String tenlop = svh.getColLop(msv);
                PhanCong phanCong = new PhanCong(msv, ten, tenphong, tenlop, ca, dateFormat.format(currentDate));
                phanCongList1.add(phanCong);
                pch.addRecord(phanCongList1);
                phanCongList.add(phanCong);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Không tồn tại mã sinh viên", Toast.LENGTH_SHORT).show();
        }
    }
}