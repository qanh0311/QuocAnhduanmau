package com.example.asm.ui.home.thanhVien;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.asm.Adapter.ReciView_Adapter_ThanhVien;
import com.example.asm.R;
import com.example.asm.SQLite.Dao.ThanhVienDao;
import com.example.asm.SQLite.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class thanhVienFragment extends Fragment {
    RecyclerView recyclerView;
    ThanhVienDao thanhVienDao;
    Dialog dialog;
    ThanhVien thanhVien;
    FloatingActionButton fab;
    EditText etMaTT,etTenTT,etNamSinh;
    Button btnSave,btnCancel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.thanh_vien_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcvTop);
        thanhVienDao = new ThanhVienDao(getActivity());


        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpen(getActivity());
            }
        });

        setData();

    }

    public void setData(){
        // set layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        //set adapter
        ReciView_Adapter_ThanhVien adapter_thanhVien = new ReciView_Adapter_ThanhVien(getActivity(),thanhVienDao.getAll(),this);
        recyclerView.setAdapter(adapter_thanhVien);
    }

    public void xoa(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xóa Thành Viên")
                .setMessage("Bạn Muốn Xóa")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        thanhVienDao.delete(id);
                        setData();
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void dialogOpen(Context context){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.item_dialog_thanhvien);

        etMaTT = dialog.findViewById(R.id.etMaTTDialog);
        etTenTT = dialog.findViewById(R.id.etTenTTDialog);
        etNamSinh = dialog.findViewById(R.id.etNamSinhDialog);
        btnCancel = dialog.findViewById(R.id.btnCancell);
        btnSave = dialog.findViewById(R.id.btnSavee);
        // check insert 0 or update 1
        etMaTT.setEnabled(false);



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                thanhVien = new ThanhVien();
                thanhVien.setHoTenTV(etTenTT.getText().toString());
                thanhVien.setNamSinh(etNamSinh.getText().toString());

                if(validate() > 0){
                        if(thanhVienDao.insert(thanhVien) > 0){
                            Toast.makeText(context, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        }

                    setData();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();

    }

    public int validate(){
        if(etTenTT.getText().toString().isEmpty() || etNamSinh.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return 1;
    }
}