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

public class ThongKe2Adapter extends RecyclerView.Adapter<ThongKe2Adapter.ViewHolder>{
    Context context;
    List<PhanCong> phanCongList;

    public ThongKe2Adapter(Context context, List<PhanCong> phanCongList) {
        this.context = context;
        this.phanCongList = phanCongList;
    }

    @NonNull
    @Override
    public ThongKe2Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layouttk2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongKe2Adapter.ViewHolder holder, int position) {
        if (phanCongList != null && phanCongList.size() > 0){
            PhanCong phanCong = phanCongList.get(position);
            holder.tv_ngaytk2.setText(phanCong.getNgay());
            holder.tv_thietbihuhaitk2.setText(phanCong.getTen());
            holder.tv_thietbithieutk2.setText(phanCong.getNote());
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return phanCongList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ngaytk2, tv_thietbihuhaitk2, tv_thietbithieutk2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ngaytk2 = itemView.findViewById(R.id.tv_ngaytk2);
            tv_thietbihuhaitk2 = itemView.findViewById(R.id.tv_thietbihuhaitk2);
            tv_thietbithieutk2 = itemView.findViewById(R.id.tv_thietbithieutk2);
        }
    }
}
