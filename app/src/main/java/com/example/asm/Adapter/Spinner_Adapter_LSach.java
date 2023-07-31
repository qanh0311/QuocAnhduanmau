package com.example.asm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asm.R;
import com.example.asm.SQLite.model.LoaiSach;

import java.util.List;

public class Spinner_Adapter_LSach extends ArrayAdapter<LoaiSach> {
    Context context;
    List<LoaiSach> list;
    TextView tvMaS, tvTenS;
    public Spinner_Adapter_LSach(@NonNull Context context, List<LoaiSach> list) {
        super(context, 0,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_spinner_sach,null);
        }
        LoaiSach loaiSach = list.get(position);

        if(loaiSach != null){
            tvMaS = view.findViewById(R.id.tvSpinnerMS);
            tvTenS = view.findViewById(R.id.tvSpinnerTS);

            tvMaS.setText(loaiSach.getMaLoaiSach() + "-");
            tvTenS.setText(loaiSach.getTenLoaiSach());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_spinner_sach,null);
        }
        LoaiSach loaiSach = list.get(position);

        if(loaiSach != null){
            tvMaS = view.findViewById(R.id.tvSpinnerMS);
            tvTenS = view.findViewById(R.id.tvSpinnerTS);

            tvMaS.setText(loaiSach.getMaLoaiSach() + "-");
            tvTenS.setText(loaiSach.getTenLoaiSach());
        }
        return view;
    }
}
