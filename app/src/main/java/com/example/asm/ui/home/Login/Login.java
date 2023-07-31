package com.example.asm.ui.home.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.asm.MainActivity;
import com.example.asm.R;
import com.example.asm.Service.checkLogin_Service;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    TextInputEditText tvTK,tvMK;
    IntentFilter intentFilter;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvTK = findViewById(R.id.etTK);
        tvMK = findViewById(R.id.etMK);
        checkBox = findViewById(R.id.checkBox);

        intentFilter = new IntentFilter();
        intentFilter.addAction("checkLogin");

        SharedPreferences preferences = getSharedPreferences("remember",MODE_PRIVATE);
        String user = preferences.getString("user","");
        String pass = preferences.getString("pass","");
        boolean re = preferences.getBoolean("remember",false);

        tvTK.setText(user);
        tvMK.setText(pass);
        checkBox.setChecked(re);
    }

    public void checkLogin(View view){
        String user = tvTK.getText().toString();
        String pass = tvMK.getText().toString();
        boolean re = checkBox.isChecked();
       if(user.isEmpty()){
           tvTK.setError("Tài Khoản Không Được Bỏ Trống");
       }else if(pass.isEmpty()){
           tvMK.setError("Mật Khẩu Không Được Bỏ Trống");
       } else {
           Intent intent = new Intent(Login.this,checkLogin_Service.class);
           intent.putExtra("tk",user);
           intent.putExtra("mk",pass);
           intent.putExtra("re",re);
           startService(intent);
       }
    }

    public void huy(View view){
        tvTK.setText("");
        tvMK.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBroadcastReceiver,intentFilter);
    }

    public void rememberUser(String u, String p, boolean status){
        SharedPreferences preferences = getSharedPreferences("remember",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(!status){
            // xoa tinh trang lưu
            editor.clear();
        }else {
            // luu du lieu
            editor.putString("user",u);
            editor.putString("pass",p);
            editor.putBoolean("remember",status);
        }
        // luu toan bo
        editor.commit();
    }

    public BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case "checkLogin" :
                    String user = intent.getStringExtra("tk");
                    String pass = intent.getStringExtra("mk");
                    boolean check = intent.getBooleanExtra("check",false);
                    if (check){
                        Toast.makeText(context, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                        rememberUser(user,pass,checkBox.isChecked());
                        Intent intent1 = new Intent(Login.this, MainActivity.class);
                        intent1.putExtra("user",user);
                        startActivity(intent1);
                        finish();
                    }else {
                        Toast.makeText(context, "Đăng Nhập Thất Bại", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    break;
                default:
                    break;
            }
        }
    };
}