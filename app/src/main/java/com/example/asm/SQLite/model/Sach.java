package com.example.asm.SQLite.model;

public class Sach {
    private  int maSach,giaThue,maLoaiSach;
    private

    String tenSach;

    public Sach() {
    }

    public Sach(int maSach, int giaThue, int maLoaiSach, String tenSach) {
        this.maSach = maSach;
        this.giaThue = giaThue;
        this.maLoaiSach = maLoaiSach;
        this.tenSach = tenSach;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getMaLoaiSach() {
        return maLoaiSach;
    }

    public void setMaLoaiSach(int maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
}
