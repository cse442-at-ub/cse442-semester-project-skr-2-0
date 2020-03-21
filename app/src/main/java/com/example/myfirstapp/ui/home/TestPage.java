package com.example.myfirstapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.myfirstapp.R;

public class TestPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //add button
        final Button editPage = findViewById(R.id.goToEditPage);
        editPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestPage.this, editTestPag.class));
            }
        });

        /*
        //ListButton
        ListView lview = (ListView) findViewById(R.id.ListOfExams);
        ArrayList<String> list = new ArrayList<String>();
        list.add("Exam 1 Time: Jan 5th 2020");
        list.add("Exam 2 TIme: Feb 25th 2020");

        ListAdapter ladapter = new ArrayAdapter<String>(this, , list);
        //lview.setAdapter(ladapter);
*/





    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
