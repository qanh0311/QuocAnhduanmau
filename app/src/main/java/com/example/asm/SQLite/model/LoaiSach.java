package com.example.asm.SQLite.model;

public class LoaiSach {
    private String tenLoaiSach;
    private int maLoaiSach;

    public LoaiSach(String tenLoaiSach, int maLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
        this.maLoaiSach = maLoaiSach;
    }

    public LoaiSach() {
    }

    public String getTenLoaiSach() {
        return tenLoaiSach;
    }

    public void setTenLoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
    }

    public int getMaLoaiSach() {
        return maLoaiSach;
    }

    public void setMaLoaiSach(int maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
    }
}
