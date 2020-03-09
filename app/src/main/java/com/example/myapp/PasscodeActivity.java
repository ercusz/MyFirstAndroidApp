package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

public class PasscodeActivity extends AppCompatActivity {
    ImageView backButton;
    MyDatabaseHelper db;
    Pinview pinview;
    TextView tvStatus;
    ImageView headerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        db = new MyDatabaseHelper(this);
        tvStatus = findViewById(R.id.tvStatus);
        pinview = findViewById(R.id.pinview);
        if(pinview.requestFocus()) {    //focus and show keyboard
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        final String username = getIntent().getStringExtra("USERNAME_FROM_DASHBOARD");//get username
        final String gateFlag = getIntent().getStringExtra("GATE_FLAG");//get gate flag

        backButton = findViewById(R.id.btnBack);        //Back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        headerImage = findViewById(R.id.imgHeader);

        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {    //input passcode and check
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                Boolean res = db.CheckUserPin(username,pinview.getValue());
                if (res==true){
                    headerImage.setImageResource(R.drawable.lock_open);
                    tvStatus.setText("รหัสลับถูกต้อง");
                    tvStatus.setTextColor(getResources().getColor(R.color.pink));
                    if(gateFlag.equals("1")){
                        Intent intent = new Intent(PasscodeActivity.this,AddPasswordActivity.class);
                        intent.putExtra("USERNAME_FROM_PASSCODE", username);
                        intent.putExtra("FROM_PW_LIST", "0");
                        startActivity(intent);
                    }
                    if(gateFlag.equals("2")){
                        Intent intent = new Intent(PasscodeActivity.this,PasswordListActivity.class);
                        intent.putExtra("USERNAME_FROM_PASSCODE", username);
                        startActivity(intent);
                    }
                    //Toast.makeText(PasscodeActivity.this,"เกิดข้อผิดพลาด ไม่สามารถไปยังหน้าต่อไปได้",Toast.LENGTH_SHORT).show();
                }
                else{
                    tvStatus.setText("รหัสลับไม่ถูกต้อง");
                    tvStatus.setTextColor(getResources().getColor(R.color.light_gray));
                }
            }
        });
    }
}
