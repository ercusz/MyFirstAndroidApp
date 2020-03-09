package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdatePasswordActivity extends AppCompatActivity {

    ImageView backButton;
    MyDatabaseHelper db;
    EditText passwordEditText;
    EditText confirmPWEditText;
    Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        backButton = findViewById(R.id.btnBack);        //Back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        db = new MyDatabaseHelper(this);    //UpdatePassword
        passwordEditText = findViewById(R.id.etPassword);
        confirmPWEditText = findViewById(R.id.etConfirmPW);
        enterButton = findViewById(R.id.btnEnter);
        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String username = getIntent().getStringExtra("USERNAME_TO_RESET_PW");
                String password = passwordEditText.getText().toString().trim();
                String confirmPW = confirmPWEditText.getText().toString().trim();
                if(password.equals(confirmPW)){
                    long val = db.updatePassword(username,password);
                    if(val>0){
                        Toast.makeText(UpdatePasswordActivity.this,"อัปเดตรหัสผ่านเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdatePasswordActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(UpdatePasswordActivity.this,"เกิดข้อผิดพลาดในการอัปเดตรหัสผ่าน",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(UpdatePasswordActivity.this,"รหัสผ่านไม่ตรงกัน",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
