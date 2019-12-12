package com.tincs.googlecertification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tincs.googlecertification.androidcore.AndroidCore;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void startAndroidCore(View view){
        startActivity(new Intent(getApplicationContext(), AndroidCore.class));
    }

}
