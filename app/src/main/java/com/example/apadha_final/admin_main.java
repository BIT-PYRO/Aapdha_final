package com.example.apadha_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class admin_main extends AppCompatActivity {

    ImageButton imageButton;
    TextView alert;
    TextView approve;
    TextView reports;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        imageButton = findViewById(R.id.imageButton);
        alert = findViewById(R.id.Live_Disaster);
        approve = findViewById(R.id.app_res);
        reports = findViewById(R.id.report_Icon);

        // Settings Button Click
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_main.this, admin_setting.class);
                startActivity(intent);
            }
        });

        // Live Disaster Alerts Click
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_main.this, admin_live.class);
                startActivity(intent);
            }
        });

        // Approve Resources Click
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_main.this, admin_resource.class);
                startActivity(intent);
            }
        });

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_main.this, admin_reports.class);
                startActivity(intent);
            }
        });
    }
}
