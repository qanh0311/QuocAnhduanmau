package com.example.asm.ui.home.loaiSach;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm.Adapter.Reciview_Adapter_LoaiSach;
import com.example.asm.R;
import com.example.asm.SQLite.Dao.LoaiSachDao;
import com.example.asm.SQLite.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class loaiSachFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    LoaiSachDao loaiSachDao;
    Dialog dialog;
    EditText eTMaLS,etTenLS;
    Button btnSave,btnCancel;
    LoaiSach loaiSach;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.loai_sach_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcvLoaiSach);
        fab = view.findViewById(R.id.fabLS);
        loaiSachDao = new LoaiSachDao(getActivity());
        loaiSach = new LoaiSach();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add(getActivity());
            }
        });
        setData();
    }

    public void setData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        Reciview_Adapter_LoaiSach adapter_loaiSach = new Reciview_Adapter_LoaiSach(getActivity(),loaiSachDao.getAll(),this);
        recyclerView.setAdapter(adapter_loaiSach);
    }

    public void add(Context context){
        dialog = new Dialog(context);

        dialog.setContentView(R.layout.item_dialog_loaisach);

        eTMaLS = dialog.findViewById(R.id.etMaLoaiSachDialog);
        etTenLS = dialog.findViewById(R.id.etTenLoaiSachDialog);
        btnSave = dialog.findViewById(R.id.btnSaveLS);
        btnCancel = dialog.findViewById(R.id.btnCancelLS);
        eTMaLS.setEnabled(false);

        // xu li

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate() > 0){
                    loaiSach.setTenLoaiSach(etTenLS.getText().toString());

                    if(loaiSachDao.insert(loaiSach) > 0){
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                setData();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public int validate(){
        if(etTenLS.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return 1;
    }

}