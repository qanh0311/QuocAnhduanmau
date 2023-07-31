package com.example.asm.ui.home.matKhau;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.SQLite.Dao.ThuThuDao;
import com.example.asm.SQLite.model.ThuThu;
import com.google.android.material.textfield.TextInputEditText;

public class matKhauFragment extends Fragment {
    TextInputEditText etMKCu,etMKMoi,etMKNhapLai;
    ThuThuDao thuThuDao;
    public matKhauFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mat_khau_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etMKCu = view.findViewById(R.id.etMKCu);
        etMKMoi = view.findViewById(R.id.etMKMoi);
        etMKNhapLai = view.findViewById(R.id.etMKNhapLai);

        thuThuDao = new ThuThuDao(getActivity());

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etMKCu.setText("");
                etMKMoi.setText("");
                etMKNhapLai.setText("");
            }
        });

        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mkMoi = etMKMoi.getText().toString();
                // lay user
                SharedPreferences preferences = getActivity().getSharedPreferences("remember",Context.MODE_PRIVATE);
                String user = preferences.getString("user","");

                if(validate() > 0 ){
                    ThuThu thuThu = thuThuDao.getId(user);
                    thuThu.setPasswork(mkMoi);
                    if( thuThuDao.updata(thuThu) > 0){
                        Toast.makeText(getContext(), "Thay Đổi Mật Khẩu Thành Công", Toast.LENGTH_SHORT).show();
                        etMKCu.setText("");
                        etMKMoi.setText("");
                        etMKNhapLai.setText("");
                    }else {
                        Toast.makeText(getContext(), "Thay Đổi Mật Khẩu Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    public int validate(){
        String mkCu = etMKCu.getText().toString();
        String mkMoi = etMKMoi.getText().toString();
        String mkLai = etMKNhapLai.getText().toString();

        if(mkCu.isEmpty() || mkMoi.isEmpty() || mkLai.isEmpty()){
            Toast.makeText(getContext(), "Cần Điều Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
            return  -1;
        }else {
            SharedPreferences preferences = getActivity().getSharedPreferences("remember", Context.MODE_PRIVATE);

            if(!mkCu.equals(preferences.getString("pass",""))){
                Toast.makeText(getContext(), "Mật Khẩu Cũ Không Đúng", Toast.LENGTH_SHORT).show();
                return -1;
            }else {
                if(!mkMoi.equals(mkLai)){
                    Toast.makeText(getContext(), "Mật Khẩu Không Trùng Với Mật Khẩu Mới", Toast.LENGTH_SHORT).show();
                    return -1;
                }
            }


        }

        return 1;
    }



}