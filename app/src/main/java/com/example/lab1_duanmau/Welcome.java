package com.example.lab1_duanmau;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lab1_duanmau.user.LoginActivity;
import com.example.lab1_duanmau.user.SignUpActivity;

public class Welcome extends AppCompatActivity {
    Button login;
    TextView txtSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        login = findViewById(R.id.btnLogin);
        txtSignUp = findViewById(R.id.txtSignup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Welcome.this, LoginActivity.class);
                startActivity(i);
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Welcome.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }
}
