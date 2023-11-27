package com.example.nhom10_chuongtrinh_ptudandroid;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom10_chuongtrinh_ptudandroid.Adapter.PhanCongAdapter;
import com.example.nhom10_chuongtrinh_ptudandroid.Adapter.ThongKe1Adapter;
import com.example.nhom10_chuongtrinh_ptudandroid.Database.PhanCongHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.PhanCong;

import java.util.ArrayList;
import java.util.List;

public class BaoCaoThongKe extends AppCompatActivity {
    PhanCongHelper pch;
    ThongKe1Adapter adapter1, adapter2;
    RecyclerView tbtk1, tbtk2;
    List<PhanCong> phanCongList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bao_cao_thong_ke);
        getFormWidget();
        if (adapter1 != null) {
            adapter1.notifyDataSetChanged();
        }
        if (adapter2 != null){
            adapter2.notifyDataSetChanged();
        }
        setRecycleView();
    }
    public void getFormWidget(){
        tbtk1 = findViewById(R.id.tbthongke1);
        tbtk2 = findViewById(R.id.tbthongke2);
        pch = new PhanCongHelper(getApplicationContext());
        if(phanCongList.isEmpty())
            phanCongList = pch.getAll();
    }
    private void setRecycleView() {
        tbtk1.setHasFixedSize(true);
        tbtk1.setLayoutManager(new LinearLayoutManager(this));
        adapter1 = new ThongKe1Adapter(this, phanCongList);
        tbtk1.setAdapter(adapter1);
//        tbtk2.setHasFixedSize(true);
//        tbtk2.setLayoutManager(new LinearLayoutManager(this));
//        adapter2 = new ThongKe2Adapter(this, phanCongList);
//        tbtk2.setAdapter(adapter2);
    }
}
