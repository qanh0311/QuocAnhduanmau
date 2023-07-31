package com.example.asm.SQLite.model;

public class Top {
    private String tenSach;
    private int soLuongSach;

    public Top(String tenSach, int soLuongSach) {
        this.tenSach = tenSach;
        this.soLuongSach = soLuongSach;
    }

    public Top() {
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getSoLuongSach() {
        return soLuongSach;
    }

    public void setSoLuongSach(int soLuongSach) {
        this.soLuongSach = soLuongSach;
    }
}
