package com.example.myapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PasswordListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    StorageAdapter adapter;
    MyDatabaseHelper database;


    List<String> storageList = new ArrayList<>();
    TextView tvUsername;
    Button addNewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String username = getIntent().getStringExtra("USERNAME_FROM_PASSCODE");//get username
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_list);
        tvUsername = findViewById(R.id.tvUsername); //display username
        tvUsername.setText(tvUsername.getText()+" "+username);

        //add button
        addNewButton = findViewById(R.id.btnAddNew);   //Go to login page
        addNewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(PasswordListActivity.this, AddPasswordActivity.class);
                intent.putExtra("USERNAME_FROM_PASSCODE", username);
                intent.putExtra("FROM_PW_LIST", "1");
                startActivity(intent);
            }
        });

        //init View
        recyclerView = findViewById(R.id.rvPassword);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //init db
        database = new MyDatabaseHelper(this);
        adapter = new StorageAdapter(this,database.getStorageByUsername(username));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        final String username = getIntent().getStringExtra("USERNAME_FROM_PASSCODE");//get username
        Intent intent = new Intent(PasswordListActivity.this, DashboardActivity.class);
        intent.putExtra("USERNAME_TO_DASHBOARD", username);
        startActivity(intent);
    }
}