package com.example.asm.SQLite.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm.SQLite.Helperr.Dbhelper;
import com.example.asm.SQLite.model.Sach;
import com.example.asm.SQLite.model.Top;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDao {
    Context context;
    SQLiteDatabase db;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public ThongKeDao(Context context) {
        this.context = context;
        this.db = new Dbhelper(context).getWritableDatabase();
    }

    // thong ke top 10
    @SuppressLint("Range")
    public List<Top> getTop10(){
        List<Top> list = new ArrayList<>();
        SachDao sachDao = new SachDao(context);

        String sql = "SELECT maSach, count(maSach) AS soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        Cursor cursor = db.rawQuery(sql,null);

        while (cursor.moveToNext()){
            Top top = new Top();
            Sach sach = sachDao.getId(cursor.getString(cursor.getColumnIndex("maSach")));
            top.setTenSach(sach.getTenSach());
            top.setSoLuongSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soLuong"))));
            list.add(top);
        }

        return list;
    }

    //thong ke doanh thu
    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay , String denNgay){
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT SUM(tienThue) AS doanhThu FROM PhieuMuon WHERE ngayMuon BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(sql,new String[]{tuNgay,denNgay});

        while (cursor.moveToNext()){
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
            }catch (Exception e){
                list.add(0);
            }
        }

      return list.get(0);
    }

}
