package com.example.apadha_final;  // Replace with your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class admin_profile extends AppCompatActivity {

    ImageView ivLogo;
    ImageView ivBack;  // Declare the ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile); // Ensure the correct XML layout is set

        // Initialize the ImageView
        ivLogo = findViewById(R.id.ivLogo);
        ivBack = findViewById(R.id.ivBack);
        // Set an OnClickListener for the ivLogo ImageView
        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to admin_main activity
                Intent intent = new Intent(admin_profile.this, admin_main.class);
                startActivity(intent);  // Start admin_main activity
            }
        });


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to navigate to admin_main activity
                Intent intent = new Intent(admin_profile.this, admin_main.class);
                startActivity(intent);  // Start admin_main activity
            }
        });
    }
}
