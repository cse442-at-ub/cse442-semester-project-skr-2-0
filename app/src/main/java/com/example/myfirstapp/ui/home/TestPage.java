package com.example.myfirstapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myfirstapp.R;

import java.util.ArrayList;


public class TestPage extends AppCompatActivity {

    private ArrayList<Exam> examList1;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        createExampleList();
        buildRecyclerView();


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

        //edit button
        final Button editPage = findViewById(R.id.goToEditPage2);
        editPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                insertItem(new Exam("Edit", 1, 2, 3));
            }
        });
    }

    public void createExampleList(){
        //Exam instantiate
        examList1 = new ArrayList<Exam>();

    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.examList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MainAdapter(examList1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertItem(Exam e){
        examList1.add(e);
        mAdapter.notifyItemInserted(examList1.size()-1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if (resultCode == RESULT_OK){
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
            }
            if(resultCode == RESULT_CANCELED){
                return;
            }
        }
    }

}
