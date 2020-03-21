package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
            doPrint();
        }

        return true;
    }

    private void doPrint(){
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.kitten);
        photoPrinter.printBitmap("Class Schedule", bitmap);
        Toast.makeText(getApplicationContext(),"Select a printer",Toast.LENGTH_SHORT).show();
    }
}
