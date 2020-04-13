package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import com.example.myfirstapp.ui.home.TestDate;
import com.example.myfirstapp.ui.home.TestPage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.print.PrintHelper;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Main3Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{              // page for schedule

    private AppBarConfiguration mAppBarConfiguration;

    private ClassDataBase db= new ClassDataBase(this,"classDateBase.qb",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_test,R.id.nav_share,R.id.nav_print,R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);

        // get current date and set it to text view 22 (current date) on class schedule page;
        Calendar cal;
        cal = Calendar.getInstance();
        String Date ;
        Date = DateFormat.getDateInstance().format(cal.getTime());
        TextView textview = findViewById(R.id.textView22);
        textview.setText(Date);

        // add button
        Button addBtn = (Button)findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(addIntent);
            }
        });
        //load db

        ArrayList<Classinfo> go= Main2Activity.giveMeTheList();
        if(!go.isEmpty()) {
            for (Classinfo classinfo : go) {
                setClassView(classinfo);
            }
        }
    }


//    private void load(){
//            Cursor cursor;
//            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
//            if(true){
//                cursor = sqLiteDatabase.rawQuery("select*from class",null);
//            }
//            if(cursor.moveToFirst()){
//                String className = cursor.getString(cursor.getColumnIndex("class_name"));
//                String classRoom= cursor.getString(cursor.getColumnIndex("class_room"));
//                int weekly = cursor.getInt(cursor.getColumnIndex("dayinweek"));
//                double start = cursor.getDouble(cursor.getColumnIndex("start"));
//                double over = cursor.getDouble(cursor.getColumnIndex("over"));
//                int mwf = cursor.getInt(cursor.getColumnIndex("mwf"));
//                int tt = cursor.getInt(cursor.getColumnIndex("tt"));
//                Classinfo classinfo = new Classinfo(className,classRoom,weekly,start,over,mwf,tt);
//                classinfoArrayList.add(classinfo);
//
//                while(cursor.moveToNext()){
//                    String className_ = cursor.getString(cursor.getColumnIndex("class_name"));
//                    String classRoom_= cursor.getString(cursor.getColumnIndex("class_room"));
//                    int weekly_ = cursor.getInt(cursor.getColumnIndex("dayinweek"));
//                    double start_ = cursor.getDouble(cursor.getColumnIndex("start"));
//                    double over_ = cursor.getDouble(cursor.getColumnIndex("over"));
//                    int mwf_ = cursor.getInt(cursor.getColumnIndex("mwf"));
//                    int tt_ = cursor.getInt(cursor.getColumnIndex("tt"));
//                    Classinfo classinfo_ = new Classinfo(className_,classRoom_,weekly_,start_,over_,mwf_,tt_);
//                    classinfoArrayList.add(classinfo_);
//                }
//            }
//                cursor.close();
//        }

    public void setClassView(Classinfo classinfo){
        double begin = classinfo.getStart();
        double end = classinfo.getOver();
        int mwf = classinfo.getMwf();
        int tt = classinfo.getTT();
        int day = classinfo.getDay();

        if(mwf == 1){
            changeColor(begin,end,"Mon",classinfo);changeColor(begin,end,"Wed",classinfo);changeColor(begin,end,"Fri",classinfo);
        }
        if(tt == 1){
            changeColor(begin,end,"Tue",classinfo);
            changeColor(begin,end,"Thu",classinfo);
        }
        if(mwf ==0 && tt ==0){
            switch (day) {
                case 0:
                    changeColor(begin, end, "Mon", classinfo);
                    break;
                case 1:
                    changeColor(begin, end, "Tue", classinfo);
                    break;
                case 2:
                    changeColor(begin, end, "Wed", classinfo);
                    break;
                case 3:
                    changeColor(begin, end, "Thu", classinfo);
                    break;
                case 4:
                    changeColor(begin, end, "Fri", classinfo);
                    break;
                case 5:
                    changeColor(begin, end, "Sat", classinfo);
                    break;
                case 6:
                    changeColor(begin, end, "Sun", classinfo);
                    break;

                default:
                    break;
            }
        }

    }
    public void changeColor(double begin, double end, String day,Classinfo classinfo){
        for (double i = begin; i < end; i = i + 0.5) {
            String btnID = day + Double.toString(i);
            int resID = getResources().getIdentifier(btnID, "id", getPackageName());
            Button btn = (Button) findViewById(resID);
            btn.setText(classinfo.getClassName());
            btn.setTextColor(0xff000000);
            btn.setBackgroundColor(0xffffff00);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem i) {
        if(i.getItemId()==R.id.nav_logout){
            Intent in=new Intent(Main3Activity.this,MainActivity.class);
            startActivity(in);
        }
        if(i.getItemId()==R.id.nav_test){
            Intent in=new Intent(Main3Activity.this,TestPage.class);
            startActivity(in);
        }

        if(i.getItemId()==R.id.nav_print){
            View view = findViewById(R.id.Schedule);
            Bitmap bitmap = ViewToBitmap(view);
            PrintHelper photoPrinter = new PrintHelper(this);
            photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            photoPrinter.printBitmap("Class Schedule", bitmap);
            Toast.makeText(getApplicationContext(),"Loading",Toast.LENGTH_SHORT).show();
            //  sample code from android studio documentation https://developer.android.com/training/printing/photos
        }

        return true;
    }
    private Bitmap ViewToBitmap(View view) {
        Bitmap image;
        image = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas;
        canvas = new Canvas(image);
        view.draw(canvas);
        return image;
    }

}
