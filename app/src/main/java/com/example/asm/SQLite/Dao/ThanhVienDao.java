package com.example.asm.SQLite.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.asm.SQLite.Helperr.Dbhelper;
import com.example.asm.SQLite.model.ThanhVien;
import com.example.asm.SQLite.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDao {
    SQLiteDatabase db;
    Context context;
    public ThanhVienDao(Context context) {
        this.db = new Dbhelper(context).getWritableDatabase();
        this.context = context;
    }

    public long insert(ThanhVien thanhVien){
        ContentValues values = new ContentValues();
        values.put("hoTenTV",thanhVien.getHoTenTV());
        values.put("namSinh",thanhVien.getNamSinh());
        return db.insert("ThanhVien",null,values);
    }

    public int updata(ThanhVien thanhVien){
        ContentValues values = new ContentValues();

        values.put("hoTenTV",thanhVien.getHoTenTV());
        values.put("namSinh",thanhVien.getNamSinh());

        return db.update("ThanhVien",values,"maTV=?",new String[]{String.valueOf(thanhVien.getMaTV())});
    }

    public int delete(String id){
        return db.delete("ThanhVien","maTV = ?",new String[]{String.valueOf(id)});
    }
    // lấy data nhiều tham số
    @SuppressLint("Range")
    public List<ThanhVien> getData(String sql, String ... selectionArgs){
        List<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while(cursor.moveToNext()){
            ThanhVien thanhVien = new ThanhVien();
            thanhVien.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV"))));
            thanhVien.setHoTenTV(cursor.getString(cursor.getColumnIndex("hoTenTV")));
            thanhVien.setNamSinh(cursor.getString(cursor.getColumnIndex("namSinh")));
            list.add(thanhVien);
        }
        return list;
    }
    // lấy tất cả data
    public List<ThanhVien> getAll(){
        String sql = "SELECT * FROM ThanhVien";
        return getData(sql);
    }
    // lấy id
    public ThanhVien getId(String id){
        String sql = " SELECT * FROM ThanhVien WHERE maTV = ?";
        List<ThanhVien> list = getData(sql,id);
        return list.get(0);
    }
}
