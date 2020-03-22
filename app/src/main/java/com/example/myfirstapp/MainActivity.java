package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myfirstapp.ui.home.TestPage;

public class MainActivity extends AppCompatActivity {               // login page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Main3Activity is home page
                Intent startIntent = new Intent(getApplicationContext(), Main3Activity.class);
                startActivity(startIntent);
            }
        });

        Button reg = (Button)findViewById(R.id.RegButton);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(getApplicationContext(), Register.class);
               startActivity(i);
            }
        });

    }
}