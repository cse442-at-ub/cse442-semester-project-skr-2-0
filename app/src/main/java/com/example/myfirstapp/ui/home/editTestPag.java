package com.example.myfirstapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfirstapp.R;

public class editTestPag extends AppCompatActivity {

    private TextView output;
    private EditText className;
    private EditText month;
    private EditText day;
    private EditText year;
    private Button b;
    private String monthT;
    private String classNameT;
    private String dayT;
    private String yearT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_page);

        //Back Button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //input fields initiation
        className = findViewById(R.id.add_className);
        month = findViewById(R.id.add_month);
        day = findViewById(R.id.add_day);
        year = findViewById(R.id.add_year);


        //error message visability
        output = findViewById(R.id.edit_error_message);
        b = findViewById(R.id.SubmitTest);

        b.setOnClickListener(new View.OnClickListener() {
            boolean visibility;
            @Override
            public void onClick(View v){
                //set visibility from gone to invis
                visibility = !visibility;

                //get the current values of the fields
                classNameT = className.getText().toString();
                monthT = month.getText().toString();
                dayT = day.getText().toString();
                yearT = year.getText().toString();

                if(classNameT.isEmpty() || monthT.isEmpty() || dayT.isEmpty() || yearT.isEmpty()) {
                    output.setText("Error: Empty Fields");
                    output.setVisibility(visibility ? View.VISIBLE : View.GONE);
                }
                else if(monthT.length() != 2 || dayT.length() != 2 || yearT.length() != 4) {
                    output.setText("Error: Date Lengths");
                    output.setVisibility(visibility ? View.VISIBLE : View.GONE);
                }
                else if(!allNum(monthT) || !allNum(dayT) || !allNum(yearT)){
                    output.setText("Error: Incorrect Date Input");
                    output.setVisibility(visibility ? View.VISIBLE : View.GONE);
                }
                else if(Integer.parseInt(monthT) > 12 || Integer.parseInt(monthT) < 1){
                    output.setText("Error: Month too high");
                    output.setVisibility(visibility ? View.VISIBLE : View.GONE);
                }
                else if(Integer.parseInt(yearT) < 2020){
                    output.setText("Error: Invalid Year");
                    output.setVisibility(visibility ? View.VISIBLE : View.GONE);
                }
                else if(Integer.parseInt(dayT) > 30){
                    output.setText("Error: Invalid Day");
                    output.setVisibility(visibility ? View.VISIBLE : View.GONE);
                }
                else{
                    submit(v);
                }
            }
        });


    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void submit(View v){
        //stores the data and prints it
        //Exam a = new Exam(classNameT, Integer.parseInt(monthT), Integer.parseInt(dayT), Integer.parseInt(yearT));
        Intent resultIntent = new Intent();
        resultIntent.putExtra("className", classNameT);
        resultIntent.putExtra("month", monthT);
        resultIntent.putExtra("day", dayT);
        resultIntent.putExtra("year", yearT);
        setResult(RESULT_OK, resultIntent);
        //startActivity(new Intent(editTestPage.this, TestPage.class));
        this.finish();
    }

    //True if all the char in the string s are numbers, false otherwise
    public boolean allNum(String s){
        for(char c : s.toCharArray()) {
            if(!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

}
