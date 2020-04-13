package com.example.myfirstapp.ui.home;

import android.content.Intent;
import android.os.Bundle;

import com.example.myfirstapp.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.R;

public class editExistingTests extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button btnSave, btnDelete;
    private TextView editable_item, editable_month, editable_year, editable_day;
    private EditText editable_1, editable_2, editable_3, editable_4;
    DatabaseHelper mDatabaseHelper;

    private String selectedName;
    private int selectedID;
    private String selectedMonth;
    private String selectedDay;
    private String selectedYear;

    private String monthT;
    private String classNameT;
    private String dayT;
    private String yearT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_page_real);

        //back button current this crashes me.
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSave = (Button) findViewById(R.id.save_edit);
        btnDelete = (Button) findViewById(R.id.delete_edit);

        editable_item = findViewById(R.id.editable_item);
        editable_month = findViewById(R.id.editable_month);
        editable_day = findViewById(R.id.editable_day);
        editable_year = findViewById(R.id.editable_year);

        mDatabaseHelper = new DatabaseHelper(this);

        //Gets the data from TestPage.java and displays it
        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedName = receivedIntent.getStringExtra("name");
        selectedMonth = receivedIntent.getStringExtra("month");
        selectedDay = receivedIntent.getStringExtra("day");
        selectedYear = receivedIntent.getStringExtra("year");
        editable_item.setText(selectedName);
        editable_month.setText("Month: " + selectedMonth);
        editable_day.setText("Day: " + selectedDay);
        editable_year.setText("Year: " + selectedYear);

        editable_1 = findViewById(R.id.edit_field1);
        editable_2 = findViewById(R.id.edit_field2);
        editable_3 = findViewById(R.id.edit_field3);
        editable_4 = findViewById(R.id.edit_field4);

        //selectedMonth = month2Num(selectedMonth);
        btnSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                classNameT = editable_1.getText().toString();
                monthT = editable_2.getText().toString();
                dayT = editable_3.getText().toString();
                yearT = editable_4.getText().toString();
                //Everything above is working

/*
                if (monthT.length() > 2 || dayT.length() > 2 || yearT.length() > 4) {//This works
                    toastMessage("Error: Date Lengths");
                } else if (!allNum(monthT) || !allNum(dayT) || !allNum(yearT)) {//This works
                    toastMessage("Error: Invalid Characters");
                } else if (Integer.parseInt(monthT) > 12 || Integer.parseInt(monthT) < 1) {
                    toastMessage("Error: Invalid Month Input");
                } else {
*/
                //if (monthT.length() > 2 || dayT.length() > 2 || yearT.length() > 4) {//This works
                //    toastMessage("Error: Date Lengths");
                //}else{
                    if (!classNameT.equals("")) {
                        mDatabaseHelper.updateName(classNameT, selectedID, selectedName);
                    }

                    if (!monthT.equals("")) {
                        mDatabaseHelper.updateMonth(numToMonth(monthT), selectedID, selectedMonth);
                    }

                    if (!dayT.equals("")) {
                        mDatabaseHelper.updateDay(dayT, selectedID, selectedDay);
                    }

                    if (!yearT.equals("")) {
                        mDatabaseHelper.updateYear(yearT, selectedID, selectedYear);
                    }
                    submit(v);

                }
            //}
        });


        btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteName(selectedID, selectedName);
                editable_item.setText("");
                toastMessage("removed from database");
                submit(v);
            }
        });


    }
    private void submit(View v){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("reload", true);
        setResult(RESULT_OK, resultIntent);

        this.finish();
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private String month2Num(String i){
        if (i.equals("Jan")){
            return "1";
        }
        if (i.equals("Feb")){
            return "2";
        }
        if (i.equals("Mar")){
            return "3";
        }
        if (i.equals("Apr")){
            return "4";
        }
        if (i.equals("May")){
            return "5";
        }
        if (i.equals("Jun")){
            return "6";
        }
        if (i.equals("Jul")){
            return "7";
        }
        if (i.equals("Aug")){
            return "8";
        }
        if (i.equals("Sep")){
            return "9";
        }
        if (i.equals("Oct")){
            return "10";
        }
        if (i.equals("Nov")){
            return "11";
        }
        if (i.equals("Dec")){
            return "12";
        }
        return i;
    }

    //helper method
    public String numToMonth(String i){
        if (i.equals("1")){
            return "Jan";
        }
        if (i.equals("2")){
            return "Feb";
        }
        if (i.equals("3")){
            return "Mar";
        }
        if (i.equals("4")){
            return "Apr";
        }
        if (i.equals("5")){
            return "May";
        }
        if (i.equals("6")){
            return "Jun";
        }
        if (i.equals("7")){
            return "Jul";
        }
        if (i.equals("8")){
            return "Aug";
        }
        if (i.equals("9")){
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
