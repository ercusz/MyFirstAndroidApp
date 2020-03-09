package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

public class DashboardActivity extends AppCompatActivity {

    MyDatabaseHelper db;
    SwipeButton addPWSwipeButton;
    SwipeButton seePWSwipeButton;
    TextView tvUsername;
    String gateFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        db = new MyDatabaseHelper(this);
        final String username = getIntent().getStringExtra("USERNAME_TO_DASHBOARD");//get username
        tvUsername = findViewById(R.id.tvUsername);
        tvUsername.setText(username);

        addPWSwipeButton = findViewById(R.id.btnAddPW); //go to add pw page
        addPWSwipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                gateFlag = "1";
                Intent intent = new Intent(DashboardActivity.this,PasscodeActivity.class);
                intent.putExtra("USERNAME_FROM_DASHBOARD", username);
                intent.putExtra("GATE_FLAG", gateFlag);
                startActivity(intent);
            }
        } );

        seePWSwipeButton = findViewById(R.id.btnSeePW); //go to see pw page
        seePWSwipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                gateFlag = "2";
                Intent intent = new Intent(DashboardActivity.this,PasscodeActivity.class);
                intent.putExtra("USERNAME_FROM_DASHBOARD", username);
                intent.putExtra("GATE_FLAG", gateFlag);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}
