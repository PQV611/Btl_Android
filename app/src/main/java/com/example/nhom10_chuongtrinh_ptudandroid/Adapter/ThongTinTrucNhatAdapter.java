package com.example.nhom10_chuongtrinh_ptudandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom10_chuongtrinh_ptudandroid.DiemDanh;
import com.example.nhom10_chuongtrinh_ptudandroid.R;
import java.util.List;

public class ThongTinTrucNhatAdapter  extends RecyclerView.Adapter<ThongTinTrucNhatAdapter.ViewHolder>{
    Context context;
    List<List<String>> tasks;
    String ca, ngay, masv;
    public ThongTinTrucNhatAdapter(Context context, List<List<String>> tasks) {
        this.context = context;
        this.tasks = tasks;
    }
    @NonNull
    @Override
    public ThongTinTrucNhatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout_thongtintrucnhat, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();

                Intent intent = new Intent(context, DiemDanh.class);
                intent.putExtra("phong", tasks.get(position).get(0));
                intent.putExtra("ca", ca);
                intent.putExtra("ngay", ngay);
                intent.putExtra("username", masv);
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ThongTinTrucNhatAdapter.ViewHolder holder, int position) {
        List<String> task = tasks.get(position);
        masv = task.get(3);
        ca = task.get(2);
        ngay = task.get(1);
        holder.tv_ca.setText(ca);
        holder.tv_ngay.setText(ngay);
        holder.tv_tenphong.setText(task.get(0));
    }
    @Override
    public int getItemCount() {
        return tasks.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tenphong, tv_ca, tv_ngay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenphong = itemView.findViewById(R.id.tv_tenphongtttn);
            tv_ngay = itemView.findViewById(R.id.tv_ngaytttn);
            tv_ca = itemView.findViewById(R.id.tv_catttn);
        }
    }
}
