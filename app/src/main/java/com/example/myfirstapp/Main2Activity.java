package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Button submitBtn = (Button)findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent submitIntent = new Intent(getApplicationContext(), Main3Activity.class);
                EditText beginTimeEditText = (EditText)findViewById(R.id.beginTimeEditText);
                //  System.out.println("darksouls"+beginTimeEditText.getText());
                // EditText endTimeEditText = (EditText)findViewById(R.id.endTimeEditText);
                submitIntent.putExtra("begin", beginTimeEditText.getText() + ""); // +"" is for "android.text.SpannableString cannot be cast to java.lang.String"
                // submitIntent.putExtra("end", endTimeEditText.getText());
                EditText endTimeEditText = (EditText)findViewById(R.id.endTimeEditText);
                submitIntent.putExtra("end", endTimeEditText.getText() + ""); // +"" is for "android.text.SpannableString cannot be cast to java.lang.String"
                CheckBox mwfCheckBox = (CheckBox)findViewById(R.id.mwfCheckBox);
                submitIntent.putExtra("mwf", mwfCheckBox.isChecked());
                startActivity(submitIntent);
            }
        });
    }
}
