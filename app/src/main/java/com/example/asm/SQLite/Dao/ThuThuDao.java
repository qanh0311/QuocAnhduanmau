package com.example.asm.SQLite.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm.SQLite.Helperr.Dbhelper;
import com.example.asm.SQLite.model.ThanhVien;
import com.example.asm.SQLite.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDao {
    private SQLiteDatabase db;
    Context context;
    public ThuThuDao(Context context) {
        this.db = new Dbhelper(context).getWritableDatabase();
        this.context = context;
    }

    public long insert(ThuThu thuThu){
        ContentValues values = new ContentValues();
        values.put("userName",thuThu.getUserName());
        values.put("passWork",thuThu.getPasswork());
        values.put("HoVaTen",thuThu.getHoVaTen());

        return db.insert("ThuThu",null,values);
    }

    public int updata(ThuThu thuThu){
        ContentValues values = new ContentValues();
        values.put("userName",thuThu.getUserName());
        values.put("passWork",thuThu.getPasswork());
        values.put("HoVaTen",thuThu.getHoVaTen());

        return db.update("ThuThu",values,"userName = ?",new String[]{String.valueOf(thuThu.getUserName())});
    }

    public int delete(String id){
        return db.delete("ThuThu","userName = ?",new String[]{String.valueOf(id)});
    }
    // lấy data nhiều tham số
    @SuppressLint("Range")
    public List<ThuThu> getData(String sql, String ... selectionArgs){
        List<ThuThu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,selectionArgs);

        while(cursor.moveToNext()){
            ThuThu thuThu = new ThuThu();
            thuThu.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
            thuThu.setPasswork(cursor.getString(cursor.getColumnIndex("passWork")));
            thuThu.setHoVaTen(cursor.getString(cursor.getColumnIndex("HoVaTen")));
            list.add(thuThu);
        }
        return list;
    }
    // lấy tất cả data
    public List<ThuThu> getAll(ThuThu thuThu){
        String sql = "SELECT * FROM ThuThu";
        return getData(sql);
    }
    // lấy id
    public ThuThu getId(String id){
        String sql = " SELECT * FROM ThuThu WHERE userName = ?";
        List<ThuThu> list = getData(sql,id);
        return list.get(0);
    }

    public boolean checkLogin(String userName, String passWork){

        String sql = "SELECT * FROM ThuThu WHERE userName = ? AND passWork = ? ";
        List<ThuThu> list =  getData(sql, userName,passWork);
        if (list.size() == 0){
            return false;
        }
        return true;
    }
}
