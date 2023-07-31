package com.example.asm.ui.home.ThemTK;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.SQLite.Dao.ThuThuDao;
import com.example.asm.SQLite.model.ThuThu;

public class ThemTKFragment extends Fragment {
    EditText etTK, etMK, etReMK,etHoTen;
    TextView tvMess;
    Button btnSave, btnCancel;
    SharedPreferences sharedPreferences;
    ThuThuDao thuThuDao;
    ThuThu thuThu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_them_t_k, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("remember", Context.MODE_PRIVATE);
        thuThu = new ThuThu();
        thuThuDao = new ThuThuDao(getActivity());
        etTK = view.findViewById(R.id.etTenDNThemTK);
        etMK = view.findViewById(R.id.etMKThemTK);
        etReMK = view.findViewById(R.id.etReMKThemTK);
        etHoTen = view.findViewById(R.id.etHoTenThemTK);
        tvMess = view.findViewById(R.id.tvMess);

        btnSave = view.findViewById(R.id.btnSaveThemTK);
        btnCancel = view.findViewById(R.id.btnCancelThemTK);

        String user = sharedPreferences.getString("user","");

        if(user.equals("admin")){
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(validate() > 0){
                        thuThu.setUserName(etTK.getText().toString());
                        thuThu.setPasswork(etMK.getText().toString());
                        thuThu.setHoVaTen(etHoTen.getText().toString());

                        if(thuThuDao.insert(thuThu) > 0){
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    etTK.setText("");
                    etMK.setText("");
                    etReMK.setText("");
                    etHoTen.setText("");
                }
            });
        }else {
            etTK.setEnabled(false);
            etMK.setEnabled(false);
            etReMK.setEnabled(false);
            etHoTen.setEnabled(false);

            tvMess.setText("Bị vô hiệu hóa bạn không phải là admin");
        }

    }

    public int validate(){
        int check = 1;
        if(etTK.getText().toString().isEmpty() || etMK.getText().toString().isEmpty() || etReMK.getText().toString().isEmpty() || etHoTen.getText().toString().isEmpty() ){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;

        }else {
            if(!etReMK.getText().toString().equals(etMK.getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp ", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}