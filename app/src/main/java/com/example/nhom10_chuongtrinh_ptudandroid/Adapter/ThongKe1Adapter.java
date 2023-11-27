package com.example.nhom10_chuongtrinh_ptudandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom10_chuongtrinh_ptudandroid.R;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.PhanCong;

import java.util.List;

public class ThongKe1Adapter extends RecyclerView.Adapter<ThongKe1Adapter.ViewHolder>{
    Context context;
    List<PhanCong> phanCongList;

    public ThongKe1Adapter(Context context, List<PhanCong> phanCongList) {
        this.context = context;
        this.phanCongList = phanCongList;
    }

    @NonNull
    @Override
    public ThongKe1Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layouttk1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongKe1Adapter.ViewHolder holder, int position) {
        if (phanCongList != null && phanCongList.size() > 0){
            PhanCong phanCong = phanCongList.get(position);
            holder.tv_msvtk1.setText(phanCong.getMasv());
            holder.tv_tentk1.setText(phanCong.getTen());
            holder.tv_trucnhattk1.setText(phanCong.getNote());
            holder.tv_catk1.setText(phanCong.getCa());
            holder.tv_ngaytk1.setText(phanCong.getNgay());
            holder.tv_loptk1.setText(phanCong.getTenLopDK());
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return phanCongList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_msvtk1, tv_tentk1, tv_trucnhattk1, tv_catk1, tv_ngaytk1, tv_loptk1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_msvtk1 = itemView.findViewById(R.id.tv_msvtk1);
            tv_tentk1 = itemView.findViewById(R.id.tv_tentk1);
            tv_trucnhattk1 = itemView.findViewById(R.id.tv_trucnhattk1);
            tv_catk1 = itemView.findViewById(R.id.tv_catk1);
            tv_ngaytk1 = itemView.findViewById(R.id.tv_ngaytk1);
            tv_loptk1 = itemView.findViewById(R.id.tv_loptk1);
        }
    }
}
