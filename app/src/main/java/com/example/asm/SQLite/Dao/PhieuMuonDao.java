package com.example.asm.SQLite.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm.SQLite.Helperr.Dbhelper;
import com.example.asm.SQLite.model.PhieuMuon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDao {
    Context context;
    SQLiteDatabase db;
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    public PhieuMuonDao(Context context) {
        this.context = context;
        this.db = new Dbhelper(context).getWritableDatabase();
    }

    public long insert( PhieuMuon phieuMuon){
        ContentValues values = new ContentValues();

        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach",phieuMuon.getMaSach());
        values.put("tienThue",phieuMuon.getTienThue());
        values.put("trangThai",phieuMuon.getTrangThai());
        values.put("userName",phieuMuon.getUserName());
        values.put("ngayMuon",format.format(phieuMuon.getNgayMuon()));

        return db.insert("PhieuMuon",null,values);
    }

    public int updata(PhieuMuon phieuMuon){
        ContentValues values = new ContentValues();

        values.put("maTV", phieuMuon.getMaTV());
        values.put("maSach",phieuMuon.getMaSach());
        values.put("tienThue",phieuMuon.getTienThue());
        values.put("trangThai",phieuMuon.getTrangThai());
        values.put("ngayMuon",format.format(phieuMuon.getNgayMuon()));

        return db.update("PhieuMuon",values,"maPM=?",new String[]{String.valueOf(phieuMuon.getMaPM())});
    }

    public int delete(String id){
        return db.delete("PhieuMuon","maPM = ?",new String[]{String.valueOf(id)});
    }
    // lấy data nhiều tham số
    @SuppressLint("Range")
    public List<PhieuMuon> getData(String sql, String ... selectionArgs){
        List<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        while(cursor.moveToNext()){
            PhieuMuon phieuMuon = new PhieuMuon();

            phieuMuon.setMaPM(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maPM"))));
            phieuMuon.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV"))));
            phieuMuon.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSach"))));
            phieuMuon.setTienThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tienThue"))));
            phieuMuon.setTrangThai(Integer.parseInt(cursor.getString(cursor.getColumnIndex("trangThai"))));
            phieuMuon.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
            try {
                phieuMuon.setNgayMuon(format.parse(cursor.getString(cursor.getColumnIndex("ngayMuon"))));
            }
            catch (Exception e){
                e.printStackTrace();
            }

            list.add(phieuMuon);
        }
        return list;
    }
    // lấy tất cả data
    public List<PhieuMuon> getAll(){
        String sql = "SELECT * FROM PhieuMuon";
        return getData(sql);
    }
    // lấy id
    public PhieuMuon getId(String id){
        String sql = " SELECT * FROM PhieuMuon WHERE maPM = ?";
        List<PhieuMuon> list = getData(sql,id);
        return list.get(0);
    }
}
