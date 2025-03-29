package com.example.apadha_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

public class UserDetailsActivity extends AppCompatActivity {

    private EditText etName, etNumber, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        etName = findViewById(R.id.etName);
        etNumber = findViewById(R.id.etNumber);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnBack = findViewById(R.id.btnBack);

        btnLogin.setOnClickListener(v -> {
            if (validateInputs()) {
                Intent intent = new Intent(UserDetailsActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }

    private boolean validateInputs() {
        String name = etName.getText().toString().trim();
        String number = etNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty() || number.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
