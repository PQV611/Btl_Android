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

import java.util.ArrayList;
import java.util.List;

public class ThongKe2Adapter extends RecyclerView.Adapter<ThongKe2Adapter.ViewHolder>{
    Context context;
    ArrayList<List<String>> resultList;

    public ThongKe2Adapter(Context context, ArrayList<List<String>> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ThongKe2Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layouttk2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongKe2Adapter.ViewHolder holder, int position) {
        if (resultList != null && resultList.size() > 0){
            List<String> task = resultList.get(position);
            holder.tv_ngaytk2.setText(task.get(0));
            holder.tv_thietbihuhaitk2.setText(task.get(1));
            holder.tv_thietbithieutk2.setText(task.get(2));
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return resultList.size();
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
