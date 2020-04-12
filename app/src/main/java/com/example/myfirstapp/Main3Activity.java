package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
        //add
        if(getIntent().hasExtra("begin")){
            String begin = getIntent().getExtras().getString("begin");
            double beginTime = changeTime(begin);
            if(beginTime < 8 || beginTime > 16.50){
                beginTime = 8.0;
            }

            String end = getIntent().getExtras().getString("end");
            double endTime = changeTime(end);

            //System.out.println("time:"+ Double.toString(beginTime));
            boolean checked = getIntent().getExtras().getBoolean("mwf");
            if(checked) {
                changeColor(beginTime, endTime, "Mon");
                changeColor(beginTime, endTime, "Wed");
                changeColor(beginTime, endTime, "Fri");
            }
            else {
                changeColor(beginTime, endTime, "Tue");
                changeColor(beginTime, endTime, "Thu");
            }
        }


    }
    public double changeTime(String time){
        double res;
        if(time.contains(":")){
            String[] hourMin = time.split(":");
            int hour = Integer.parseInt(hourMin[0]);
            double mins = Integer.parseInt(hourMin[1]);
            if(mins != 0 && mins != 30){
                mins = 0; // lazy coding
            }
            res = hour + mins / 60;
        }
        else{
            res = Integer.parseInt(time);
        }
        return res;


    }

    public void changeColor(double begin, double end, String day ){
        for (double i = begin; i < end; i = i + 0.5) {
            String btnID = day + Double.toString(i);
            //System.out.println(btnID);
            //System.out.println(endTime);
            int resID = getResources().getIdentifier(btnID, "id", getPackageName());
            Button btn = (Button) findViewById(resID);
            int red=0xffff0000;
            btn.setBackgroundColor(red);
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
