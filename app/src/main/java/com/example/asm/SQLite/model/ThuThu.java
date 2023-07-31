package com.example.asm.SQLite.model;

public class ThuThu {
    private String userName , passwork, HoVaTen;

    public ThuThu(String userName, String passwork, String hoVaTen) {
        this.userName = userName;
        this.passwork = passwork;
        HoVaTen = hoVaTen;
    }

    public ThuThu() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswork() {
        return passwork;
    }

    public void setPasswork(String passwork) {
        this.passwork = passwork;
    }

    public String getHoVaTen() {
        return HoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        HoVaTen = hoVaTen;
    }
}
