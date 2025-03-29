package com.example.apadha_final;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "sos_channel";
    private static final int NOTIFICATION_ID = 1;
    private static final int REQUEST_PERMISSION_CODE = 101;

    private FirebaseFirestore db;
    private Button sosButton;
    private ImageView ivNotification, ivSlider;
    private Button btnFireRescue, btnAmbulance, btnHospital, btnPolice;
    private Button btnFoodWater, btnShelter, btnRescue, btnMedical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
        db = FirebaseFirestore.getInstance();

        // Find UI Elements
        sosButton = findViewById(R.id.btnEmergencySOS);
        ivNotification = findViewById(R.id.ivNotification);
        ivSlider = findViewById(R.id.ivSlider);
        btnFireRescue = findViewById(R.id.btnFireRescue);
        btnAmbulance = findViewById(R.id.btnAmbulance);
        btnHospital = findViewById(R.id.btnHospital);
        btnPolice = findViewById(R.id.btnPolice);
        btnFoodWater = findViewById(R.id.btnFoodWater);
        btnShelter = findViewById(R.id.btnShelter);
        btnRescue = findViewById(R.id.btnRescue);
        btnMedical = findViewById(R.id.btnMedical);

        // Navigate to LoginActivity when Notification Button is Clicked
        ivNotification.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Set Button Click Listeners
        sosButton.setOnClickListener(v -> requestNotificationPermission()); // 🔹 Calls notification function
        btnFireRescue.setOnClickListener(v -> showToast("Fire Rescue Service"));
        btnAmbulance.setOnClickListener(v -> showToast("Ambulance Service"));
        btnHospital.setOnClickListener(v -> showToast("Hospital Service"));
        btnPolice.setOnClickListener(v -> showToast("Police Service"));
        btnFoodWater.setOnClickListener(v -> showToast("Food and Water Services"));
        btnShelter.setOnClickListener(v -> showToast("Shelter Services"));
        btnRescue.setOnClickListener(v -> showToast("Rescue Services"));
        btnMedical.setOnClickListener(v -> showToast("Medical Services"));
    }

    // ✅ Create Notification Channel (Required for Android 8.0+)
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Emergency SOS", NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notification for SOS Alert");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    // ✅ Check & Request Permission for Notifications (Android 13+)
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_PERMISSION_CODE
                );
            } else {
                sendSOSNotification(); // 🔹 If permission already granted
            }
        } else {
            sendSOSNotification(); // 🔹 Directly send for Android < 13
        }
    }

    // ✅ Show Emergency SOS Notification
    private void sendSOSNotification() {
        // Check if the app has permission before sending the notification
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Notification permission is required!", Toast.LENGTH_SHORT).show();
            return; // 🔹 Stop execution if permission is not granted
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_sos) // Ensure this drawable exists
                .setContentTitle("🚨 Emergency Alert")
                .setContentText("Your emergency details have been sent!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build()); // 🔹 Show Notification
    }

    // ✅ Handle Permission Request Result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendSOSNotification(); // 🔹 Retry sending notification if granted
        } else {
            Toast.makeText(this, "Permission denied! Cannot send notification.", Toast.LENGTH_SHORT).show();
        }
    }

    // ✅ Helper method to show a toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
