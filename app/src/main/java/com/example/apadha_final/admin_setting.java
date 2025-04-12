package com.example.apadha_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class admin_setting extends AppCompatActivity {

    ImageView back_button;
    TextView profile, tvLogout; // ✅ Add logout text view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_setting);

        back_button = findViewById(R.id.back_button);
        profile = findViewById(R.id.ivProfile);
        tvLogout = findViewById(R.id.tvLogout); // ✅ Find by ID

        back_button.setOnClickListener(v -> {
            startActivity(new Intent(admin_setting.this, admin_main.class));
            finish(); // Optional: finish this activity
        });

        profile.setOnClickListener(v -> {
            startActivity(new Intent(admin_setting.this, admin_profile.class));
            finish(); // Optional: finish this activity
        });

        // ✅ Logout → Go to LoginActivity
        tvLogout.setOnClickListener(v -> {
            Intent intent = new Intent(admin_setting.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
            startActivity(intent);
            finish(); // Finish current activity
        });
    }
}
