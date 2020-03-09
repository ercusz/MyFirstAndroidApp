package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ResetPasswordActivity extends AppCompatActivity {

    ImageView backButton;
    MyDatabaseHelper db;
    EditText usernameEditText;
    EditText pinEditText;
    Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        backButton = findViewById(R.id.btnBack);        //Back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        db = new MyDatabaseHelper(this);    //ResetPassword
        usernameEditText = findViewById(R.id.etUsername);
        pinEditText = findViewById(R.id.etPin);
        enterButton = findViewById(R.id.btnEnter);
        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String username = usernameEditText.getText().toString().trim();
                String pin = pinEditText.getText().toString().trim();
                Boolean res = db.CheckUserPin(username,pin);
                if(res==true){
                    Toast.makeText(ResetPasswordActivity.this,"รหัสลับถูกต้อง",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetPasswordActivity.this, UpdatePasswordActivity.class);
                    intent.putExtra("USERNAME_TO_RESET_PW", username);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ResetPasswordActivity.this,"รหัสลับผิด กรุณากรอกใหม่อีกครั้ง",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
