package com.example.apadha_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class admin_setting extends AppCompatActivity {

    ImageView back_button;
    TextView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_setting);

        back_button = findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_setting.this, admin_main.class);
                startActivity(intent);
                finish(); // optional, closes current activity
            }
        });

        profile = findViewById(R.id.ivProfile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_setting.this, admin_profile.class);
                startActivity(intent);
                finish(); // optional, closes current activity
            }
        });
    }
}
