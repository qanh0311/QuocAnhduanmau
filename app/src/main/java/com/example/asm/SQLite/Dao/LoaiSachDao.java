package com.example.asm.SQLite.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm.SQLite.Helperr.Dbhelper;
import com.example.asm.SQLite.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDao {
    SQLiteDatabase db;
    Context context;
    public LoaiSachDao(Context context) {
        this.db = new Dbhelper(context).getWritableDatabase();
        this.context = context;
    }

    public long insert(LoaiSach loaiSach){
        ContentValues values = new ContentValues();
        values.put("tenLoaiSach", loaiSach.getTenLoaiSach());
        return db.insert("LoaiSach",null,values);
    }

    public int updata(LoaiSach loaiSach){
        ContentValues values = new ContentValues();
        values.put("tenLoaiSach", loaiSach.getTenLoaiSach());
        return db.update("LoaiSach",values,"maLoaiSach = ?",new String[]{String.valueOf(loaiSach.getMaLoaiSach())});
    }

    public int delete(String id){
        return db.delete("LoaiSach","maLoaiSach = ? ",new String[]{String.valueOf(id)});
    }
    // lấy data nhiều tham số
    @SuppressLint("Range")
    public List<LoaiSach> getData(String sql, String ... selectionArgs){
        List<LoaiSach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while(cursor.moveToNext()){
            LoaiSach loaiSach = new LoaiSach();
            loaiSach.setMaLoaiSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maLoaiSach"))));
            loaiSach.setTenLoaiSach(cursor.getString(cursor.getColumnIndex("tenLoaiSach")));
            list.add(loaiSach);
        }
        return list;
    }
    // lấy tất cả data
    public List<LoaiSach> getAll(){
        String sql = "SELECT * FROM LoaiSach";
        return getData(sql);
    }
    // lấy id
    public LoaiSach getId(String id){
        String sql = " SELECT * FROM LoaiSach WHERE maLoaiSach = ?";
        List<LoaiSach> list = getData(sql,id);
        return list.get(0);
    }
}
