package com.tincs.googlecertification.androidcore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.tincs.googlecertification.R;

public class AndroidCore extends AppCompatActivity implements View.OnClickListener{

    TextView custom_toast, snackbar, notifications;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_core);

        //init views
        custom_toast = findViewById(R.id.custom_toast);
        snackbar = findViewById(R.id.snackbar);
        notifications = findViewById(R.id.notifications);

        custom_toast.setOnClickListener(this);
        snackbar.setOnClickListener(this);
        notifications.setOnClickListener(this);
    }

    // here is the method that enable you to display custom toast

    public void customToast(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)findViewById(R.id.custom_toast_container));
        TextView textView = layout.findViewById(R.id.text);
        textView.setText("This is a custom Toast");
        Toast toast = new Toast(getApplicationContext());
        toast.setView(layout);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    // here is the method that enable you to display Snackbar wit an action attached

    public void showSnackBar(){
        Snackbar.make(custom_toast, "Snackbar",Snackbar.LENGTH_LONG)
                .setAction("Toast", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            customToast();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.custom_toast: customToast();break;
            case R.id.snackbar: showSnackBar();break;
            case R.id.notifications: startActivity(new Intent(getApplicationContext(), NotifActivity.class));break;
        }
    }
}
