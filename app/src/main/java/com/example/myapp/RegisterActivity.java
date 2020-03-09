package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    ImageView backButton;
    TextView loginText;
    MyDatabaseHelper db;
    EditText usernameEditText;
    EditText passwordEditText;
    EditText confirmPWEditText;
    EditText pinEditText;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        backButton = findViewById(R.id.btnBack);        //Back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loginText = findViewById(R.id.tvLogin);   //Go to login page
        loginText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        db = new MyDatabaseHelper(this);    //Register
        usernameEditText = findViewById(R.id.etUsername);
        passwordEditText = findViewById(R.id.etPassword);
        confirmPWEditText = findViewById(R.id.etConfirmPW);
        pinEditText = findViewById(R.id.etPin);
        registerButton = findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPW = confirmPWEditText.getText().toString().trim();
                String pin = pinEditText.getText().toString().trim();

                if(password.equals(confirmPW)){
                    long val = db.addUser(username,password,pin);
                    if(val>0){
                        Toast.makeText(RegisterActivity.this,"สมัครสมาชิกสำเร็จแล้ว",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"เกิดข้อผิดพลาดในการสมัครสมาชิก",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(RegisterActivity.this,"รหัสผ่านไม่ตรงกัน",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
