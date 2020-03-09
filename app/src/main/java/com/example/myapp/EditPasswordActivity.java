package com.example.myapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditPasswordActivity extends AppCompatActivity {
    EditText titleEditText, stUsernameEditText, stPasswordEditText, descEditText;
    String id,username,title,st_username,st_password,description;
    Button updatePasswordButton, cancelButton;
    TextView deleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        titleEditText = findViewById(R.id.etTiTle);
        stUsernameEditText = findViewById(R.id.etStUsername);
        stPasswordEditText = findViewById(R.id.etStPassword);
        descEditText = findViewById(R.id.etDesc);
        updatePasswordButton = findViewById(R.id.btnUpdatePW);
        deleteTextView = findViewById(R.id.tvDelete);

        //First we call this
        getAndSetIntentData();

        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                MyDatabaseHelper myDB = new MyDatabaseHelper(EditPasswordActivity.this);
                title = titleEditText.getText().toString().trim();
                st_username = stUsernameEditText.getText().toString().trim();
                st_password = stPasswordEditText.getText().toString().trim();
                description = descEditText.getText().toString().trim();
                boolean res = myDB.updateData(id, username, title, st_username, st_password, description);
                if(res==true) {
                    Intent intent = new Intent(EditPasswordActivity.this, PasswordListActivity.class);
                    intent.putExtra("USERNAME_FROM_PASSCODE", username);
                    startActivity(intent);
                }
            }
        });

        deleteTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

        cancelButton = findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(EditPasswordActivity.this, PasswordListActivity.class);
                intent.putExtra("USERNAME_FROM_PASSCODE", username);
                startActivity(intent);
            }
        });
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("st_username") && getIntent().hasExtra("st_password")&& getIntent().hasExtra("description")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            username = getIntent().getStringExtra("username");
            title = getIntent().getStringExtra("title");
            st_username = getIntent().getStringExtra("st_username");
            st_password = getIntent().getStringExtra("st_password");
            description = getIntent().getStringExtra("description");

            //Setting Intent Data
            titleEditText.setText(title);
            stUsernameEditText.setText(st_username);
            stPasswordEditText.setText(st_password);
            descEditText.setText(description);
            Log.d("stev", title+" "+st_username+" "+st_password+" "+description);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ต้องการลบ " + title + " ใช่หรือไม่?");
        builder.setMessage("หากลบแล้วจะไม่สามารถกู้คืนได้อีก");
        builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(EditPasswordActivity.this);
                myDB.deleteOneRow(id);
                finish();
                Intent intent = new Intent(EditPasswordActivity.this, PasswordListActivity.class);
                intent.putExtra("USERNAME_FROM_PASSCODE", username);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
