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

public class PhanCongAdapter extends RecyclerView.Adapter<PhanCongAdapter.ViewHolder>{
    Context context;
    List<PhanCong> phanCongList;

    public PhanCongAdapter(Context context, List<PhanCong> phanCongList) {
        this.context = context;
        this.phanCongList = phanCongList;
    }

    @NonNull
    @Override
    public PhanCongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhanCongAdapter.ViewHolder holder, int position) {
        if (phanCongList != null && phanCongList.size() > 0){
            PhanCong phanCong = phanCongList.get(position);
            holder.tv_msvphancong.setText(phanCong.getMasv());
            holder.tv_tenphancong.setText(phanCong.getTen());
            holder.tv_notephancong.setText(phanCong.getNote());
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return phanCongList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_msvphancong, tv_tenphancong, tv_notephancong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_msvphancong = itemView.findViewById(R.id.tv_msvphancong);
            tv_tenphancong = itemView.findViewById(R.id.tv_tenphancong);
            tv_notephancong = itemView.findViewById(R.id.tv_notephancong);
        }
    }
}
