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
import com.example.asm.SQLite.model.ThanhVien;

import java.util.List;

public class Spinner_Adapter_TV extends ArrayAdapter<ThanhVien> {
    Context context;
    List<ThanhVien> list;
    TextView tvMTV,tvTTV;
    public Spinner_Adapter_TV(@NonNull Context context,List<ThanhVien> list ) {
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
            view = inflater.inflate(R.layout.item_spinner_tv,null);
        }
        ThanhVien thanhVien = list.get(position);
        if (thanhVien != null){
            tvMTV = view.findViewById(R.id.tvSpinnerMTV);
            tvTTV = view.findViewById(R.id.tvSpinnerTTV);

            tvMTV.setText(thanhVien.getMaTV() + "-");
            tvTTV.setText(thanhVien.getHoTenTV());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_spinner_tv,null);
        }
        ThanhVien thanhVien = list.get(position);
        if (thanhVien != null){
            tvMTV = view.findViewById(R.id.tvSpinnerMTV);
            tvTTV = view.findViewById(R.id.tvSpinnerTTV);

            tvMTV.setText(thanhVien.getMaTV() +"-");
            tvTTV.setText(thanhVien.getHoTenTV());
        }
        return view;
    }
}
