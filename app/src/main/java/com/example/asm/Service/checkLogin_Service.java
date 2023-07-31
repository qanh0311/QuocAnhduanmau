package com.example.asm.Service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.asm.SQLite.Dao.ThuThuDao;

public class checkLogin_Service extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String user = intent.getStringExtra("tk");
        String pass = intent.getStringExtra("mk");


        ThuThuDao thuThuDao = new ThuThuDao(this);
        boolean check = thuThuDao.checkLogin(user,pass);

        Intent intentBR = new Intent();
        intentBR.putExtra("check",check);
        intentBR.putExtra("tk",user);
        intentBR.putExtra("mk",pass);
        intentBR.setAction("checkLogin");
        sendBroadcast(intentBR);

        return super.onStartCommand(intent, flags, startId);
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
