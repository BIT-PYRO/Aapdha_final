package com.example.apadha_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class admin_resource extends AppCompatActivity {

    ImageView back;

    CardView card1, card2, card3;
    TextView btnApprove1, btnReject1;
    TextView btnApprove2, btnReject2;
    TextView btnApprove3, btnReject3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_resource);

        // Back button
        back = findViewById(R.id.back_icon);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(admin_resource.this, admin_main.class);
            startActivity(intent);
            finish();
        });

        // Card references
        card1 = findViewById(R.id.cardItem1);
        card2 = findViewById(R.id.cardItem2);
        card3 = findViewById(R.id.cardItem3);

        // Button references
        btnApprove1 = findViewById(R.id.btnApprove1);
        btnReject1 = findViewById(R.id.btnReject1);
        btnApprove2 = findViewById(R.id.btnApprove2);
        btnReject2 = findViewById(R.id.btnReject2);
        btnApprove3 = findViewById(R.id.btnApprove3);
        btnReject3 = findViewById(R.id.btnReject3);

        // Card 1 logic
        btnApprove1.setOnClickListener(v -> {
            Toast.makeText(this, "Request 1 approved", Toast.LENGTH_SHORT).show();
            card1.setVisibility(View.GONE);
        });

        btnReject1.setOnClickListener(v -> {
            Toast.makeText(this, "Request 1 rejected", Toast.LENGTH_SHORT).show();
            card1.setVisibility(View.GONE);
        });

        // Card 2 logic
        btnApprove2.setOnClickListener(v -> {
            Toast.makeText(this, "Request 2 approved", Toast.LENGTH_SHORT).show();
            card2.setVisibility(View.GONE);
        });

        btnReject2.setOnClickListener(v -> {
            Toast.makeText(this, "Request 2 rejected", Toast.LENGTH_SHORT).show();
            card2.setVisibility(View.GONE);
        });

        // Card 3 logic
        btnApprove3.setOnClickListener(v -> {
            Toast.makeText(this, "Request 3 approved", Toast.LENGTH_SHORT).show();
            card3.setVisibility(View.GONE);
        });

        btnReject3.setOnClickListener(v -> {
            Toast.makeText(this, "Request 3 rejected", Toast.LENGTH_SHORT).show();
            card3.setVisibility(View.GONE);
        });
    }
}
