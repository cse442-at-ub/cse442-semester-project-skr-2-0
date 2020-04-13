package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText editTextUserName,  editTextPassword, editTextEmail;
    private Button buttonRegister;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        editTextUserName = (EditText) findViewById(R.id.editTextUsername);;
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        progressDialog = new ProgressDialog(this);
        //buttonRegister.setOnClickListener();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textViewMessage = (TextView) findViewById(R.id.textViewMessage);
                if(view == buttonRegister){
                    if(editTextUserName.getText().length()<1){
                        textViewMessage.setText("error: UserName is empty");
                        textViewMessage.setVisibility(true ? View.VISIBLE : View.GONE);
                        return;
                    }
                    if(editTextPassword.getText().length()<6) {
                        textViewMessage.setText("password: at least 6 characters");
                        textViewMessage.setVisibility(true ? View.VISIBLE : View.GONE);
                        return;
                    }
                    if(editTextEmail.getText().length()<6) {
                        textViewMessage.setText("Email: at least 6 characters");
                        textViewMessage.setVisibility(true ? View.VISIBLE : View.GONE);
                        return;
                    }
                    textViewMessage.setVisibility(false? View.VISIBLE : View.GONE);
                    registerUser();
                }
            }
        });
    }
    private void registerUser() {
        final String username = editTextUserName.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("email",email);
                return params;
            }
        };

        //RequestQueue requestQueue = Volley.newRequestQueue(this);   //deleted for step 7
        //requestQueue.add(stringRequest); //deleted for step 7
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
