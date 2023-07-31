package com.example.asm.SQLite.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm.SQLite.Helperr.Dbhelper;
import com.example.asm.SQLite.model.LoaiSach;
import com.example.asm.SQLite.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDao {
    SQLiteDatabase db;
    Context context;
    public SachDao(Context context) {
        this.db = new Dbhelper(context).getWritableDatabase();
        this.context = context;
    }

    public long insert(Sach sach){
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.getTenSach());
        values.put("giaThue", sach.getGiaThue());
        values.put("maLoaiSach", sach.getMaLoaiSach());

        return db.insert("Sach",null,values);
    }

    public int updata(Sach sach){
        ContentValues values = new ContentValues();
        values.put("tenSach", sach.getTenSach());
        values.put("giaThue", sach.getGiaThue());
        values.put("maLoaiSach", sach.getMaLoaiSach());

        return db.update("Sach",values,"maSach=?",new String[]{String.valueOf(sach.getMaSach())});
    }

    public int delete(String id){
        return db.delete("Sach","maSach = ?",new String[]{String.valueOf(id)});
    }
    // lấy data nhiều tham số
    @SuppressLint("Range")
    public List<Sach> getData(String sql, String ... selectionArgs){
        List<Sach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while(cursor.moveToNext()){
            Sach sach = new Sach();
            sach.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSach"))));
            sach.setTenSach(cursor.getString(cursor.getColumnIndex("tenSach")));
            sach.setGiaThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("giaThue"))));
            sach.setMaLoaiSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maLoaiSach"))));
            list.add(sach);
        }
        return list;
    }
    // lấy tất cả data
    public List<Sach> getAll(){
        String sql = "SELECT * FROM Sach";
        return getData(sql);
    }
    // lấy id
    public Sach getId(String id){
        String sql = " SELECT * FROM Sach WHERE maSach = ?";
        List<Sach> list = getData(sql,id);
        return list.get(0);
    }
}
