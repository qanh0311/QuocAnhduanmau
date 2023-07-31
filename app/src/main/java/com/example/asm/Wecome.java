package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.asm.ui.home.Login.Login;

public class Wecome extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wecome);

        imageView = findViewById(R.id.iVGif);

        Glide.with(this)
                .load("https://cdn.dribbble.com/users/154752/screenshots/1244719/book.gif")
                .into(imageView);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Wecome.this, Login.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}