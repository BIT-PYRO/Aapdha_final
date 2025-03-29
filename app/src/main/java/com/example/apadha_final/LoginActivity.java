package com.example.apadha_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("UserLogins");

        // Input Fields
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        // Login Button - Navigates to MainActivity
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeLoginData(); // Store data in Firebase
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close LoginActivity so it doesn't stay in the back stack
            }
        });

        // Signup Button - Navigates to SignupActivity
        Button btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void storeLoginData() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Storing login data in Firebase Realtime Database
        String userId = databaseReference.push().getKey();
        databaseReference.child(userId).setValue(new UserLogin(email, password))
                .addOnSuccessListener(aVoid -> Toast.makeText(LoginActivity.this, "Login data stored", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Failed to store data", Toast.LENGTH_SHORT).show());
    }

    // Class to structure login data
    public static class UserLogin {
        public String email, password;

        public UserLogin() {
            // Default constructor required for Firebase
        }

        public UserLogin(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}
