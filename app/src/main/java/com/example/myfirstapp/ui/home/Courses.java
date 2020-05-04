package com.example.myfirstapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myfirstapp.Classinfo;
import com.example.myfirstapp.Main2Activity;
import com.example.myfirstapp.R;

import java.util.ArrayList;

public class Courses extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);
        TextView textview = findViewById(R.id.Courses);
        final ArrayList<Classinfo> classinfo=Main2Activity.giveMeTheList();
        for (int i=0;i<classinfo.size();i++){
            Classinfo course=classinfo.get(i);
            String Days = "";

            if (course.getMwf() == 1) {
                Days=Days+"MON&";
                Days=Days+"WED&";
                Days=Days+"FRI";
            }
            if (course.getTT() == 1) {
                Days=Days+"TUE&";
                Days=Days+"THU";
            }
            if (course.getMwf() == 0 && course.getTT() == 0) {
                switch (course.getDay()) {
                    case 0:
                        Days = Days + "MON";
                        break;
                    case 1:
                        Days = Days + "TUE";
                        break;
                    case 2:
                        Days = Days + "WED";
                        break;
                    case 3:
                        Days = Days + "THU";
                        break;
                    case 4:
                        Days = Days + "FRI";
                        break;
                    case 5:
                        Days = Days + "SAT";
                        break;
                    case 6:
                        Days = Days + "SUN";
                        break;
                    default:
                        break;
                }
            }
            int start= (int) course.getStart();
            String half1="00";
            if(start-course.getStart()!=0)
                half1="30";
            int end= (int) course.getOver();
            String half2="00";
            if(end-course.getOver()!=0)
                half2="30";

            textview.append(course.getClassName() + ", " + course.getClassRoom() + "\n" +Days+"\n"+
                    start+":"+half1+
                    "-"+end+":"+half2
                    +"\n");
            textview.append("\n");
        }
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        //TextView tvId = (TextView) findViewById(R.id.Courses);
//
//        return inflater.inflate(R.layout.course_activity, container, false);
//    }
}
