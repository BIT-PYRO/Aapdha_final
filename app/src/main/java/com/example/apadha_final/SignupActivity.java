package com.example.apadha_final;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText firstNameInput, contactInput;
    private Button submitButton;
    private TextView loginLink;
    private ImageView backButton;

    private DatabaseReference databaseReference;

    private EditText roleInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("UserSignups");

        // Bind views
        firstNameInput = findViewById(R.id.firstNameInput); // Using as "username"
        contactInput = findViewById(R.id.contactInput);     // Using as "email"
        submitButton = findViewById(R.id.submitButton);
        loginLink = findViewById(R.id.loginLink);
        backButton = findViewById(R.id.backButton);

        roleInput = findViewById(R.id.roleInput);
        roleInput.setOnClickListener(v -> showRolePopup());


        // Submit: store name and email → go to ReportActivity
        submitButton.setOnClickListener(v -> {
            String username = firstNameInput.getText().toString().trim();
            String email = contactInput.getText().toString().trim();

            String userId = databaseReference.push().getKey();
            SimpleUser user = new SimpleUser(username, email);

            if (userId != null) {
                databaseReference.child(userId).setValue(user);
            }

            // Navigate to ReportActivity
            startActivity(new Intent(SignupActivity.this, MainActivity2.class));
            finish();
        });

        // Login link
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


    // Firebase model class
    public static class SimpleUser {
        public String username;
        public String email;

        public SimpleUser() {
            // Required default constructor
        }

        public SimpleUser(String username, String email) {
            this.username = username;
            this.email = email;
        }
    }
}
