package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPasswordActivity extends AppCompatActivity {

    Button cancelButton;
    Button addPWButton;
    MyDatabaseHelper db;
    EditText titleEditText;
    EditText stUsernameEditText;
    EditText stPasswordEditText;
    EditText descEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        final String username = getIntent().getStringExtra("USERNAME_FROM_PASSCODE");//get username
        final String fromPwList = getIntent().getStringExtra("FROM_PW_LIST");//get flag
        cancelButton = findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(fromPwList.equals("1")){
                    Intent intent = new Intent(AddPasswordActivity.this, PasswordListActivity.class);
                    intent.putExtra("USERNAME_FROM_PASSCODE", username);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(AddPasswordActivity.this, DashboardActivity.class);
                    intent.putExtra("USERNAME_TO_DASHBOARD", username);
                    startActivity(intent);
                }
            }
        });

        db = new MyDatabaseHelper(this);    //Add PW to storage
        titleEditText = findViewById(R.id.etTiTle);
        stUsernameEditText = findViewById(R.id.etStUsername);
        stPasswordEditText = findViewById(R.id.etStPassword);
        descEditText = findViewById(R.id.etDesc);
        addPWButton = findViewById(R.id.btnAddPW);
        addPWButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String title = titleEditText.getText().toString().trim();
                String stusername = stUsernameEditText.getText().toString().trim();
                String stpassword = stPasswordEditText.getText().toString().trim();
                String desc = descEditText.getText().toString().trim();
                long val = db.addToStorage(username,title,stusername,stpassword,desc);
                if(val>0){
                    if(fromPwList.equals("1")){
                        Intent intent = new Intent(AddPasswordActivity.this, PasswordListActivity.class);
                        intent.putExtra("USERNAME_FROM_PASSCODE", username);
                        startActivity(intent);
                    }else{
                        Toast.makeText(AddPasswordActivity.this,"บันทึกรหัสผ่านเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddPasswordActivity.this,DashboardActivity.class);
                        intent.putExtra("USERNAME_TO_DASHBOARD", username);
                        startActivity(intent);
                    }

                }
                else{
                    Toast.makeText(AddPasswordActivity.this,"เกิดข้อผิดพลาดในการเพิ่มรหัสผ่าน",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
