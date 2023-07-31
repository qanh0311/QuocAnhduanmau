package com.example.asm.ui.home.phieuMuon;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm.Adapter.Reciview_Adapter_PhieuMuon;
import com.example.asm.Adapter.Spinner_Adapter_Sach;
import com.example.asm.Adapter.Spinner_Adapter_TV;
import com.example.asm.R;
import com.example.asm.SQLite.Dao.PhieuMuonDao;
import com.example.asm.SQLite.Dao.SachDao;
import com.example.asm.SQLite.Dao.ThanhVienDao;
import com.example.asm.SQLite.model.PhieuMuon;
import com.example.asm.SQLite.model.Sach;
import com.example.asm.SQLite.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class phieuMuonFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    PhieuMuonDao  phieuMuonDao;
    SachDao sachDao;
    ThanhVienDao thanhVienDao;

    Dialog dialog;
    EditText etMaPM ;
    TextView  etNgayMuon, etTienThue;
    Button btnSave,btnCacel;
    Spinner spinnerS, spinnerTV;
    CheckBox checkBoxPM;
    int maS,maTV,tienthue;
    List<Sach> listS;
    List<ThanhVien> listTV;
    PhieuMuon phieuMuon;
    SharedPreferences sharedPreferences;

    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.phieu_muon_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phieuMuonDao = new PhieuMuonDao(getActivity());
        sachDao = new SachDao(getActivity());
        thanhVienDao = new ThanhVienDao(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("remember", Context.MODE_PRIVATE);
        recyclerView = view.findViewById(R.id.rcv_PM);
        fab = view.findViewById(R.id.fabPM);

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
        Reciview_Adapter_PhieuMuon adapterPhieuMuon =
                new Reciview_Adapter_PhieuMuon(getActivity(),phieuMuonDao.getAll(),this);
        recyclerView.setAdapter(adapterPhieuMuon);
    }

    public void add(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.item_dialog_pm);

        etMaPM = dialog.findViewById(R.id.etMaPMDialog);
        etNgayMuon =dialog.findViewById(R.id.etNgayMuonDialog);
        etTienThue = dialog.findViewById(R.id.etTienThueDialog);
        spinnerS = dialog.findViewById(R.id.spinnerLSPM);
        spinnerTV = dialog.findViewById(R.id.spinnerTVPM);
        btnSave = dialog.findViewById(R.id.btnSavePM);
        btnCacel = dialog.findViewById(R.id.btnCancelPM);
        checkBoxPM = dialog.findViewById(R.id.checkBoxPM);
        //spiner
        etMaPM.setEnabled(false);

        listS = sachDao.getAll();
        Spinner_Adapter_Sach adapterSach = new Spinner_Adapter_Sach(getActivity(),listS);
        spinnerS.setAdapter(adapterSach);
        spinnerS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maS = listS.get(i).getMaSach();
                tienthue = listS.get(i).getGiaThue();
                etTienThue.setText("Tiền thuê : " + tienthue );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listTV = thanhVienDao.getAll();
        Spinner_Adapter_TV adapterTV = new Spinner_Adapter_TV(getActivity(),listTV);
        spinnerTV.setAdapter(adapterTV);
        spinnerTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maTV = listTV.get(i).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // set ngay
        etNgayMuon.setText("Ngày mượn : "+format.format(new Date()));

        // xu li
        btnCacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phieuMuon = new PhieuMuon();
                phieuMuon.setMaTV(maTV);
                phieuMuon.setMaSach(maS);
                phieuMuon.setNgayMuon(new Date());
                phieuMuon.setTienThue(tienthue);
                phieuMuon.setUserName(sharedPreferences.getString("user",""));
                if(checkBoxPM.isChecked()){
                    phieuMuon.setTrangThai(0);
                }else {
                    phieuMuon.setTrangThai(1);
                }
                if(phieuMuonDao.insert(phieuMuon) > 0){
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();

                    setData();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });


        dialog.show();

    }
}