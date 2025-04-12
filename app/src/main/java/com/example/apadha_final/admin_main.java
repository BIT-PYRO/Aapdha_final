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

    private boolean isSettingsClicked = false; // ✅ Prevent multiple clicks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        imageButton = findViewById(R.id.imageButton);
        alert = findViewById(R.id.Live_Disaster);
        approve = findViewById(R.id.app_res);
        reports = findViewById(R.id.report_Icon);

        // ✅ Settings Button (Menu Icon)
        imageButton.setOnClickListener(v -> {
            if (!isSettingsClicked) {
                isSettingsClicked = true;
                Intent intent = new Intent(admin_main.this, admin_setting.class);
                startActivity(intent);
            }
        });

        // Live Disaster Alerts
        alert.setOnClickListener(v -> {
            Intent intent = new Intent(admin_main.this, admin_live.class);
            startActivity(intent);
        });

        // Approve Resources
        approve.setOnClickListener(v -> {
            Intent intent = new Intent(admin_main.this, admin_resource.class);
            startActivity(intent);
        });

        // Reports
        reports.setOnClickListener(v -> {
            Intent intent = new Intent(admin_main.this, admin_reports.class);
            startActivity(intent);
        });
    }

    // ✅ Reset flag when returning to this activity
    @Override
    protected void onResume() {
        super.onResume();
        isSettingsClicked = false;
    }
}
