package com.example.apadha_final;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

    // ✅ Hardcoded admin credentials
    private final String ADMIN_EMAIL = "admin@gmail.com";
    private final String ADMIN_PASSWORD = "123AS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseReference = FirebaseDatabase.getInstance().getReference("UserLogins");

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Validate input
                if (!isValidEmail(email)) {
                    Toast.makeText(LoginActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // ✅ Check if admin login
                if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
                    Toast.makeText(LoginActivity.this, "Welcome Admin!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, admin_main.class);
                    startActivity(intent);
                    finish();
                    return;
                }

                // Store login data for regular users
                storeLoginData(email, password);

                // Navigate to MainActivity2
                Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });

        Button btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void storeLoginData(String email, String password) {
        String userId = databaseReference.push().getKey();
        databaseReference.child(userId).setValue(new UserLogin(email, password))
                .addOnSuccessListener(aVoid -> Toast.makeText(LoginActivity.this, "Login data stored", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Failed to store data", Toast.LENGTH_SHORT).show());
    }

    public static class UserLogin {
        public String email, password;

        public UserLogin() {
        }

        public UserLogin(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}
