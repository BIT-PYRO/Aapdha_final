package com.example.apadha_final;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText firstNameInput, lastNameInput, contactInput, roleInput, passwordInput;
    private Button submitButton;
    private TextView loginLink;
    private ImageView backButton;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Connect to Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("UserSignups");

        // Bind views
        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        contactInput = findViewById(R.id.contactInput);
        roleInput = findViewById(R.id.roleInput);
        passwordInput = findViewById(R.id.passwordInput); // Add a password field
        submitButton = findViewById(R.id.submitButton);
        loginLink = findViewById(R.id.loginLink);
        backButton = findViewById(R.id.backButton);

        // Show role popup
        roleInput.setOnClickListener(v -> showRolePopup());

        // Submit signup details to Firebase
        submitButton.setOnClickListener(v -> {
            String firstName = firstNameInput.getText().toString().trim();
            String lastName = lastNameInput.getText().toString().trim();
            String email = contactInput.getText().toString().trim();
            String role = roleInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            // Validate inputs
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || role.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate email format
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate password strength (minimum 6 characters)
            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create user with Firebase Authentication
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Send verification email
                            user.sendEmailVerification()
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignupActivity.this, "Verification email sent!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(SignupActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            // Store user data in Firebase Realtime Database
                            String userId = databaseReference.push().getKey();
                            if (userId != null) {
                                UserSignup fullUser = new UserSignup(firstName, lastName, email, role);
                                databaseReference.child(userId).setValue(fullUser)
                                        .addOnSuccessListener(aVoid -> Toast.makeText(SignupActivity.this, "Signup saved!", Toast.LENGTH_SHORT).show())
                                        .addOnFailureListener(e -> Toast.makeText(SignupActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show());
                            }

                            // Navigate to login screen
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("SignupError", "Signup failed", e);
                        Toast.makeText(SignupActivity.this, "Signup failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });

        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });

        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void showRolePopup() {
        final String[] roles = {"Citizen", "Volunteer", "NGO", "Authority"};
        PopupMenu popup = new PopupMenu(this, roleInput);
        for (String role : roles) {
            popup.getMenu().add(role);
        }
        popup.setOnMenuItemClickListener(item -> {
            roleInput.setText(item.getTitle());
            return true;
        });
        popup.show();
    }

    public static class UserSignup {
        public String firstName;
        public String lastName;
        public String email;
        public String role;

        public UserSignup() {
            // Required for Firebase deserialization
        }

        public UserSignup(String firstName, String lastName, String email, String role) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.role = role;
        }
    }
}
