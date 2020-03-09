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

public class LoginActivity extends AppCompatActivity {

    ImageView backButton;
    TextView registerTextView;
    TextView forgetPwTextView;
    MyDatabaseHelper db;
    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backButton = findViewById(R.id.btnBack);        //Back
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        registerTextView = findViewById(R.id.tvRegister);   //Go to register page
        registerTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgetPwTextView = findViewById(R.id.tvForgetPW);   //Go to forget password page
        forgetPwTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        db = new MyDatabaseHelper(this);    //Login
        usernameEditText = findViewById(R.id.etUsername);
        passwordEditText = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                Boolean res = db.checkUser(username,password);
                if(res==true){
                    Toast.makeText(LoginActivity.this,"เข้าสู่ระบบเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.putExtra("USERNAME_TO_DASHBOARD", username);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this,"เกิดข้อผิดพลาดในการเข้าสู่ระบบ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
