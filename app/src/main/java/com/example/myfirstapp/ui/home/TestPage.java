package com.example.myfirstapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myfirstapp.DatabaseHelper;
import com.example.myfirstapp.R;

import java.time.Month;
import java.util.ArrayList;


public class TestPage extends AppCompatActivity {

    private ArrayList<Exam> examList1;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainAdapter mAdapter;
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //database
        mListView = (ListView) findViewById(R.id.examlistview);
        mDatabaseHelper = new DatabaseHelper(this);
        //mDatabaseHelper.clearDatabase();
        //crashing me
        populateListView();

        /*
        createExampleList();
        buildRecyclerView();
        */



        //back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //add button
        final Button addPage = findViewById(R.id.goToAddPage);
        addPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(TestPage.this, editTestPag.class), 1);
            }
        });

/*
        //edit button
        final Button editPage = findViewById(R.id.goToEditPage2);
        editPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent editScreenIntent = new Intent(TestPage.this, editExistingTests.class);
                startActivity(editScreenIntent);
            }
        });
 */


    }

    /*
    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MainAdapter(examList1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    */

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if (resultCode == RESULT_OK){
                /*
                String n = data.getStringExtra("className");
                String m = data.getStringExtra("month");
                String d = data.getStringExtra("day");
                String y = data.getStringExtra("year");
                Exam e = new Exam(n, Integer.parseInt(m), Integer.parseInt(d), Integer.parseInt(y));
                //This is crashing me
                insertItem(e);
                //examList.add(e);
                //mAdapter.notifyItemInserted(examList.size()-1);
                //refresh();
                 */

                boolean result = data.getBooleanExtra("reload", false);
                if (result){
                    populateListView();
                }
            }
            if(resultCode == RESULT_CANCELED){
                return;
            }
        }
    }

    //refreshes data in the database
    private void populateListView(){


        Cursor data = mDatabaseHelper.getData();

        ArrayList<String> listData = new ArrayList<String>();

        while(data.moveToNext()){
            String fullInfo = data.getString(1) + " Date: " + data.getString(2) + " " +
                    data.getString(3) + " " + data.getString(4);
            //set a different for each dataset???
            /*
            Exam e = new Exam(data.getString(1), Integer.parseInt(data.getString(2)),
                    Integer.parseInt(data.getString(3)), Integer.parseInt(data.getString(4)));
             */
            listData.add(fullInfo);
        }

        //create list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        //set onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                //Set a different string for each dataset??
                String clss = NameSeperator(name);
                String _yr = YearSeperator(name);
                String _day = DaySeperator(name);
                String _month = MonthSeperator(name);

                Cursor data = mDatabaseHelper.getID(clss);
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if (itemID > -1){
                    Intent editScreenIntent = new Intent(TestPage.this, editExistingTests.class);
                    editScreenIntent.putExtra("id", itemID);
                    editScreenIntent.putExtra("name", clss);
                    editScreenIntent.putExtra("month", _month);
                    editScreenIntent.putExtra("day", _day);
                    editScreenIntent.putExtra("year", _yr);
                    //startActivity(editScreenIntent);
                    startActivityForResult(editScreenIntent, 1);

                }
                else{
                    toastMessage("No ID associated with that name");
                }


            }


        });

    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //helper method
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

    public String YearSeperator(String str){
        String[] splitter = str.split(" ", 0);
        int size = splitter.length;
        return splitter[size - 1];
    }

    public String MonthSeperator(String str){
        String[] splitter = str.split(" ", 0);
        int size = splitter.length;
        return splitter[size - 3];
    }

    public String DaySeperator(String str){
        String[] splitter = str.split(" ", 0);
        int size = splitter.length;
        return splitter[size - 2];
    }

    public String NameSeperator(String str){
        String[] splitter = str.split(" ", 0);
        int size = splitter.length - 4;
        String retVal = "";
        for(int i = 0; i < size; i++){
            retVal = retVal + splitter[i];
        }
        return retVal;
    }
}
