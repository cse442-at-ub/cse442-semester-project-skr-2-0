package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {
    private static ArrayList<Classinfo> classInfoArrayList = new ArrayList<Classinfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Spinner spinner = findViewById(R.id.spinner_date);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Spinner_Weekly_view,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        final EditText input_name = (EditText) findViewById(R.id.classEditText);
        final EditText input_room = (EditText) findViewById(R.id.Room_number);
        final EditText input_start = (EditText) findViewById(R.id.beginTimeEditText);
        final EditText input_over = (EditText) findViewById(R.id.endTimeEditText);
        final Button submitBtn = (Button)findViewById(R.id.submitBtn);
        final CheckBox checkBox1 = (CheckBox) findViewById(R.id.mwfCheckBox);
        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.ttcheckbox);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = input_name.getText().toString();
                String start = input_start.getText().toString();
                String over = input_over.getText().toString();
                String room = input_room.getText().toString();
                double begin =8;
                double end =9;
                int day =0;
                int ismwf=0;
                int istt= 0;
                if( name.equals("")){
                    Toast.makeText(Main2Activity.this,"Missing Class Name",Toast.LENGTH_SHORT).show();
                }else if(room.equals("")){
                    Toast.makeText(Main2Activity.this,"Missing Room Number",Toast.LENGTH_SHORT).show();
                }else if(start.equals("")){
                    Toast.makeText(Main2Activity.this,"Missing Start Time",Toast.LENGTH_SHORT).show();
                }else if(over.equals("")){
                    Toast.makeText(Main2Activity.this,"Missing End Time",Toast.LENGTH_SHORT).show();
                }else if(!isTimeValid(start) || !isTimeValid(over)){
                    Toast.makeText(Main2Activity.this,"Time syntax error",Toast.LENGTH_SHORT).show();
                }else{
                    begin = TimeToDouble(start);
                    end = TimeToDouble(over);
                    day = spinner.getSelectedItemPosition();
                    if (checkBox1.isChecked()){
                        ismwf = 1;
                    }
                    if(checkBox2.isChecked()){
                        istt = 1;
                    };

                    if(end<begin){
                        Toast.makeText(Main2Activity.this,"You can't end before you start, try again",Toast.LENGTH_LONG).show();
                    }else if(begin == end){
                        Toast.makeText(Main2Activity.this,"The Start time should not be the same as the End time",Toast.LENGTH_LONG).show();
                    }else if(begin < 8.0 || begin > 21.5 || end <8.0 || end > 21.5){
                        Toast.makeText(Main2Activity.this,"Exceed time limit",Toast.LENGTH_SHORT).show();
                    }else{
                        Intent submitIntent = new Intent(getApplicationContext(), Main3Activity.class);
                        Classinfo classinfo = new Classinfo(name,room,day,begin,end,ismwf,istt);
                        classInfoArrayList.add(classinfo);
                        double bein = classinfo.getStart();
                        startActivity(submitIntent);
                    }

                }
            }
        });
    }

    public boolean isTimeValid(String time) {
        double res;
        if (time.contains(":")) {
            String[] hourMin = time.split(":");
            int hour = Integer.parseInt(hourMin[0]);
            double Mins = Integer.parseInt(hourMin[1]);
            if (Mins == 0 || Mins == 30) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }
    public double TimeToDouble(String time){
        double res;
        if(time.contains(":")){
            String[] hourMin = time.split(":");
            int hour = Integer.parseInt(hourMin[0]);
            double mins = Integer.parseInt(hourMin[1]);
            res = hour + mins / 60;
        }
        else{
            res = Integer.parseInt(time);
        }
        return res;
    }
    public static ArrayList<Classinfo> giveMeTheList(){
        return classInfoArrayList;
    }

//    public void save(Classinfo classinfo){
//        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
//        String path = "insert into class(class_name,class_room,dayinweek,start, over, mwf,tt) ";
//        String value = "values(?, ?, ?, ?, ?, ?,?)";
//        String[] info={classinfo.getClassName(),classinfo.getClassRoom(),classinfo.getDay()+"",classinfo.getStart()+"",classinfo.getOver()+"",classinfo.getMwf()+"",classinfo.getTT()+""};
//        sqLiteDatabase.execSQL(path + value,info);
//    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
