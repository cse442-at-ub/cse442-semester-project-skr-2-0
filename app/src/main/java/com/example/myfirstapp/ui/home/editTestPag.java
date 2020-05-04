package com.example.myfirstapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.DatabaseHelper;
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
    DatabaseHelper mDatabaseHelper;

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
                else if (Integer.parseInt(dayT) > 31 && Integer.parseInt(dayT) > 0){
                    output.setText("Error: Invalid Day");
                    output.setVisibility(visibility ? View.VISIBLE : View.GONE);
                }
                else{
                    submit(v);
                }
            }
        });

        mDatabaseHelper = new DatabaseHelper(this);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //change storing to store in SQLite database
    public void submit(View v){
        //stores the data and prints it
        //Exam a = new Exam(classNameT, Integer.parseInt(monthT), Integer.parseInt(dayT), Integer.parseInt(yearT));
        /*
        Intent resultIntent = new Intent();
        resultIntent.putExtra("className", classNameT);
        resultIntent.putExtra("month", monthT);
        resultIntent.putExtra("day", dayT);
        resultIntent.putExtra("year", yearT);
        setResult(RESULT_OK, resultIntent);

         */
        AddData(classNameT, monthT, dayT, yearT);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("reload", true);
        setResult(RESULT_OK, resultIntent);


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

    //Database stuff
    public void AddData(String cN, String mon, String d, String yr){

        //addData crashes me ahh
        boolean insertData = mDatabaseHelper.addData(cN, numToMonth(mon), d, yr);

        if(insertData){
            toastM("Data Successfully Inserted!");
        }
        else{
            toastM("Something went wrong");
        }


    }

    private void toastM(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public String numToMonth(String i){
        if (i.equals("01") || i.equals("1")){
            return "Jan";
        }
        if (i.equals("02") || i.equals("2")){
            return "Feb";
        }
        if (i.equals("03") || i.equals("3")){
            return "Mar";
        }
        if (i.equals("04") || i.equals("4")){
            return "Apr";
        }
        if (i.equals("05") || i.equals("5")){
            return "May";
        }
        if (i.equals("06") || i.equals("6")){
            return "Jun";
        }
        if (i.equals("07") || i.equals("7")){
            return "Jul";
        }
        if (i.equals("08") || i.equals("8")){
            return "Aug";
        }
        if (i.equals("09") || i.equals("9")){
            return "Sep";
        }
        if (i.equals("10")){
            return "Oct";
        }
        if (i.equals("11")){
            return "Nov";
        }
        if (i.equals("12")){
            return "Dec";
        }
        return i;
    }

}