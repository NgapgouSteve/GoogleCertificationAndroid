package com.tincs.googlecertification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread start = new Thread(){
            public void run(){
                try {
                    sleep(4000);
                }catch (InterruptedException ab){
                    ab.printStackTrace();
                }finally {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        };
        start.start();
    }
}
