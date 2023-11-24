package com.example.nhom10_chuongtrinh_ptudandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.nhom10_chuongtrinh_ptudandroid.Adapter.PhanCongAdapter;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.PhanCong;

import java.util.ArrayList;
import java.util.List;

public class PhanCongTrucNhat extends AppCompatActivity{
    EditText editMSV, editTenSV, editNote;
    Button btnThem;
    RecyclerView tbphancong;
    PhanCongAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phan_cong_truc_nhat);
        getFormWidget();
        setRecycleView();
    }
    public void getFormWidget(){
        editMSV = findViewById(R.id.editMSV);
        editTenSV = findViewById(R.id.editTenSV);
        editNote = findViewById(R.id.editNote);
        btnThem = findViewById(R.id.btnThem);
        tbphancong = findViewById(R.id.tbphancong);
    }
    private void setRecycleView() {
        tbphancong.setHasFixedSize(true);
        tbphancong.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PhanCongAdapter(this, getList());
        tbphancong.setAdapter(adapter);
    }

    private List<PhanCong> getList() {
        List<PhanCong> phanCongList = new ArrayList<>();
        phanCongList.add(new PhanCong("2021602643", "Trần Minh Phương Trần Minh Phương Trần Minh Phương Trần Minh Phương Trần Minh Phương Trần Minh Phương", "Đi học muộn", "123", "123", "123"));
        return phanCongList;
    }
}
