package com.example.asm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.R;
import com.example.asm.SQLite.model.Top;

import java.util.List;

public class Receiview_Adapter_Top extends RecyclerView.Adapter<Receiview_Adapter_Top.viewHolder> {

    Context context;
    List<Top> list;

    public Receiview_Adapter_Top(Context context, List<Top> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcv_top,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if(list != null){
            holder.tvTenSachTop.setText("Tên sách : " + list.get(position).getTenSach() + "-");
            holder.tvSLSachTop.setText("Số lượng mượn : " + list.get(position).getSoLuongSach());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView tvSLSachTop, tvTenSachTop;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvSLSachTop = itemView.findViewById(R.id.tvSLSachTop);
            tvTenSachTop = itemView.findViewById(R.id.tvTenSachTop);
        }
    }
}
