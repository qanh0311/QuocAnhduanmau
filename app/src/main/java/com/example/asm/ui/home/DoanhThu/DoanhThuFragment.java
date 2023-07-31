package com.example.asm.ui.home.DoanhThu;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asm.R;
import com.example.asm.SQLite.Dao.ThongKeDao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DoanhThuFragment extends Fragment {
    ThongKeDao thongKeDao;
    TextView tvDT;
    Button btnTuN,btnDenN,btnDT;
    EditText etTuN,etDenN;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    int myear,mmonth,mday;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        thongKeDao = new ThongKeDao(getActivity());
        tvDT = view.findViewById(R.id.tvDoanhThu);
        btnTuN = view.findViewById(R.id.btnTuN);
        btnDenN = view.findViewById(R.id.btnDenN);
        btnDT = view.findViewById(R.id.btnDoanhThu);
        etTuN = view.findViewById(R.id.etTuN);
        etDenN = view.findViewById(R.id.etDenN);

        etTuN.setEnabled(false);
        etDenN.setEnabled(false);

        btnTuN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                myear = calendar.get(Calendar.YEAR);
                mmonth = calendar.get(Calendar.MONTH);
                mday = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),0,mTuNgay,myear,mmonth,mday);
                d.show();
            }
        });
        btnDenN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                myear = calendar.get(Calendar.YEAR);
                mmonth = calendar.get(Calendar.MONTH);
                mday = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),0,mDenNgay,myear,mmonth,mday);
                d.show();
            }
        });

        btnDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tuNgay = etTuN.getText().toString();
                String denNgay = etDenN.getText().toString();

                tvDT.setText("Doanh Thu : " + thongKeDao.getDoanhThu(tuNgay,denNgay));
            }
        });
    }

    DatePickerDialog.OnDateSetListener mTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            mday = i2;
            mmonth =i1;
            myear = i;
            GregorianCalendar c = new GregorianCalendar(myear,mmonth,mday);
            etTuN.setText(simpleDateFormat.format(c.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener mDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            mday = i2;
            mmonth =i1;
            myear = i;
            GregorianCalendar c = new GregorianCalendar(myear,mmonth,mday);
            etDenN.setText(simpleDateFormat.format(c.getTime()));
        }
    };
}