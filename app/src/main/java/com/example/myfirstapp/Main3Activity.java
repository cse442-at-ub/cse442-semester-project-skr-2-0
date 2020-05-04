package com.example.myfirstapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;

import com.example.myfirstapp.ui.home.Courses;
import com.example.myfirstapp.ui.home.MainFragment;
import com.example.myfirstapp.ui.home.TestDate;
import com.example.myfirstapp.ui.home.TestPage;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Main3Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{              // page for schedule

    private AppBarConfiguration mAppBarConfiguration;

    private ClassDataBase classDataBase = new ClassDataBase(this);

    private TextView textViewUsername, textViewUserEmail; //added by fuming

    Dialog myDialog;
    AlertDialog alertDiag;

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
                R.id.nav_home,R.id.nav_test,R.id.nav_course,R.id.nav_print,R.id.nav_logout)
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

        setButtonListener(Main2Activity.giveMeTheList());
        load();//load db
        ArrayList<Classinfo> go= Main2Activity.giveMeTheList();
        if(!go.isEmpty()) {
            for (Classinfo classinfo : go) {
                setClassView(classinfo);
            }
        }

        myDialog = new Dialog(this);

        //added by fuming
        //NavigationView nV = (NavigationView) findViewById(R.id.nav_view);
        //View headerView = nV.getHeaderView(0);
        //textViewUsername = (TextView) headerView.findViewById(R.id.textViewHeaderUsernam);
        //textViewUsername.setText(SharedPreManager.getInstance(this).getUsername());

    }



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

    public void setButtonListener(final ArrayList<Classinfo> classinfo){
        final double begin = 8.0;
        final double end =21.5;
        final String days[] = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        for(int i = 0 ; i< days.length;i++){
            for(double j = begin;j<end;j = j+ 0.5){
                String BtnID = days[i]+Double.toString(j);
                int resID = getResources().getIdentifier(BtnID, "id", getPackageName());
                final Button btn = (Button) findViewById(resID);
                btn.setBackgroundResource(android.R.drawable.btn_default_small);
                btn.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        String x = btn.getText().toString();
                        boolean ifexist = false;
                        for (int i = 0; i<classinfo.size();i++){   // check if current button has a name.
                            String Tem = classinfo.get(i).getClassName();
                            if (Tem == x){
                                ifexist = true;
                                classinfo.remove(i);
                                SQLiteDatabase sqLiteDatabase =  classDataBase.getWritableDatabase();
                                String[] blank = {Tem};
                                String delete = "delete from class_schedule where NAME = ?";
                                sqLiteDatabase.execSQL(delete,blank);
                            }
                        }
                        if(ifexist == false){
                            Toast.makeText(Main3Activity.this,"You don't have class at this time !",Toast.LENGTH_SHORT).show();
                            btn.setBackgroundResource(android.R.drawable.btn_default_small);
                            btn.setText("");
                            return true;
                        }else if (ifexist == true){
                            for(int i = 0 ; i <days.length;i++){
                                for(double j = begin;j<end;j = j+ 0.5){
                                    String Button = days[i]+Double.toString(j);
                                    int ID = getResources().getIdentifier(Button, "id", getPackageName());
                                    Button dbtn = (Button) findViewById(ID);
                                    if(dbtn.getText() == x){
                                        dbtn.setBackgroundResource(android.R.drawable.btn_default_small);
                                        dbtn.setText("");

                                    }
                                }
                            }
                                Toast.makeText(Main3Activity.this,"Deleted !!",Toast.LENGTH_SHORT).show();
                                return true;
                        }

                        return true;
                    }
                });
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String x = btn.getText().toString();
                        int index = -1;
                        boolean ifexist = false;
                        for (int i = 0; i<classinfo.size();i++) {   // check if current button has a name.
                            String Tem = classinfo.get(i).getClassName();
                            Classinfo course = classinfo.get(i);
                            if (Tem == x) {
                                ifexist = true;
                                index = i;
                            }
                        }
                            if (ifexist){



                                ShowPopup(v, index);
                            }
                            else{
                                Toast.makeText(Main3Activity.this, "No class exists at this time", Toast.LENGTH_SHORT).show();
                        }//this is not looping

                    }
                });
            }
        }
    }

    public void ShowPopup(View v, final int index){
        SQLiteDatabase sqLiteDatabase =  classDataBase.getWritableDatabase();
        TextView txtclose;
        Button btnEdit;
        final TextView noteText;
        final EditText editText = new EditText(this);
        myDialog.setContentView(R.layout.note_popup);
        noteText = myDialog.findViewById(R.id.note_text);
        txtclose = myDialog.findViewById(R.id.note_close);

        final ArrayList<Classinfo> classList = Main2Activity.giveMeTheList();
        String className = classList.get(index).getClassName();

        //check if the button exists
        //if it exists then do what is below
        //set noteText to the current text or default "sample note"
        if (index == -1){
            Toast.makeText(Main3Activity.this, "No class exists, how'd you even get in here?", Toast.LENGTH_SHORT).show();
        }
        else{
            //SET THE NOTE TO BE WHATEVER THE DATABASE HAS NOTE TO BE
            noteText.setText(classList.get(index).getNote());
        }

        alertDiag = new AlertDialog.Builder(this).create();
        alertDiag.setTitle(" Edit your notes ");
        alertDiag.setView(editText);
        alertDiag.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE TEXT", new DialogInterface.OnClickListener(){
            //this click happens right after you click save text
            @Override
            public void onClick(DialogInterface dialog, int which) {
                noteText.setText(editText.getText());
                //set the note for the class you clicked in the database to editText.getText()
                Main2Activity.setNoteatIndex(index, editText.getText().toString());
                classList.get(index).setNote(editText.getText().toString());
            }
        });

        //this click happens when you click the note to take you into editable form (I dont need to change anything here anymore)
        noteText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editText.setText(noteText.getText());
                alertDiag.show();
            }
        });

        txtclose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myDialog.dismiss();
            }
        });

        myDialog.show();

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

        if(i.getItemId()==R.id.nav_course){
            Intent in=new Intent(Main3Activity.this, Courses.class);
            startActivity(in);
        }

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

    public void load(){
        Main2Activity.giveMeTheList().clear();
        SQLiteDatabase sqLiteDatabase = classDataBase.getWritableDatabase();
        String x = "select * from class_schedule";
        Cursor cursor = sqLiteDatabase.rawQuery(x,null);
        if(cursor.moveToFirst()){
            String class_name = cursor.getString(cursor.getColumnIndex("NAME"));
            String class_room = cursor.getString(cursor.getColumnIndex("ROOM"));
            int class_day = Integer.parseInt(cursor.getString(cursor.getColumnIndex("DAY")));
            double class_begin = Double.parseDouble(cursor.getString(cursor.getColumnIndex("BG")));
            double class_over = Double.parseDouble(cursor.getString(cursor.getColumnIndex("OVER")));
            int mwf = Integer.parseInt(cursor.getString(cursor.getColumnIndex("MWF")));
            int tt = Integer.parseInt(cursor.getString(cursor.getColumnIndex("TT")));
            Classinfo classinfo = new Classinfo(class_name,class_room,class_day,class_begin,class_over,mwf,tt,"press to edit me");
            Main2Activity.addclass(classinfo);
            while(cursor.moveToNext()){
                String class_name0 = cursor.getString(cursor.getColumnIndex("NAME"));
                String class_room0 = cursor.getString(cursor.getColumnIndex("ROOM"));
                int class_day0 = Integer.parseInt(cursor.getString(cursor.getColumnIndex("DAY")));
                double class_begin0 = Double.parseDouble(cursor.getString(cursor.getColumnIndex("BG")));
                double class_over0 = Double.parseDouble(cursor.getString(cursor.getColumnIndex("OVER")));
                int mwf0 = Integer.parseInt(cursor.getString(cursor.getColumnIndex("MWF")));
                int tt0 = Integer.parseInt(cursor.getString(cursor.getColumnIndex("TT")));
                Classinfo classinfo0 = new Classinfo(class_name0,class_room0,class_day0,class_begin0,class_over0,mwf0,tt0,"");
                Main2Activity.addclass(classinfo0);
            };
        }
        cursor.close();

    }


}
