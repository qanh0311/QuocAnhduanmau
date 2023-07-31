package com.example.asm.ui.home.sach;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asm.Adapter.Reciview_Adapter_Sach;
import com.example.asm.Adapter.Spinner_Adapter_LSach;
import com.example.asm.R;
import com.example.asm.SQLite.Dao.LoaiSachDao;
import com.example.asm.SQLite.Dao.SachDao;
import com.example.asm.SQLite.model.LoaiSach;
import com.example.asm.SQLite.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class sachFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    SachDao sachDao;
    LoaiSachDao loaiSachDao;
    Dialog dialog;
    EditText eTMaS,etTenS,etGiaThue;
    Button btnSave,btnCancel;
    Spinner spinner;
    List<LoaiSach> listLS;
    Spinner_Adapter_LSach spinnerAdapterSach;
    int maLS;
    Sach sach;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sach_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sachDao = new SachDao(getActivity());
        sach = new Sach();
        loaiSachDao = new LoaiSachDao(getActivity());
        recyclerView = view.findViewById(R.id.rcv_sach);
        fab = view.findViewById(R.id.fabSach);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        setData();
    }

    public void setData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        Reciview_Adapter_Sach adapterSach = new Reciview_Adapter_Sach(getActivity(),sachDao.getAll(),this);
        recyclerView.setAdapter(adapterSach);
    }
    public void add(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.item_dialog_sach);
        eTMaS = dialog.findViewById(R.id.etMaSachDialog);
        etTenS = dialog.findViewById(R.id.etTenSachDialog);
        etGiaThue = dialog.findViewById(R.id.etGiaThueDialog);
        spinner = dialog.findViewById(R.id.spinnerLS);
        btnSave = dialog.findViewById(R.id.btnSaveS);
        btnCancel = dialog.findViewById(R.id.btnCancelS);
        eTMaS.setEnabled(false);
        // spinner
        listLS = loaiSachDao.getAll();
        spinnerAdapterSach = new Spinner_Adapter_LSach(getActivity(),listLS);
        spinner.setAdapter(spinnerAdapterSach);
        // lay ma ls
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maLS = listLS.get(i).getMaLoaiSach();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // xu li

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()>0){
                    sach.setTenSach(etTenS.getText().toString());
                    sach.setGiaThue(Integer.parseInt(etGiaThue.getText().toString()));
                    sach.setMaLoaiSach(maLS);

                    if(sachDao.insert(sach) > 0){
                        Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                    setData();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();

    }
    public int validate(){
        if(etTenS.getText().toString().isEmpty() || etGiaThue.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return 1;
    }


}