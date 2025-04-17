package com.example.apadha_final;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "sos_channel";
    private static final int NOTIFICATION_ID = 1;

    Button emergencyButton;
    ImageButton notificationsButton, menuButton;
    ImageView reportingButton;

    // Contact Icons
    ImageView imgFireRescue, imgAmbulance, imgHospital, imgPolice;

    // Service Icons
    ImageView imgFoodWater, imgShelter, imgRescue, imgMedical;

    // Info Icons
    ImageView imgSafetyTips, imgDisasterHelpline, imgFirstAidTips, imgNGOVolunteer;

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        // UI Bindings
        emergencyButton = findViewById(R.id.emergencyButton);
        notificationsButton = findViewById(R.id.notificationsButton);
        reportingButton = findViewById(R.id.bannerImage);
        menuButton = findViewById(R.id.menuButton);

        // Contact Section
        imgFireRescue = findViewById(R.id.imgFireRescue);
        imgAmbulance = findViewById(R.id.imgAmbulance);
        imgHospital = findViewById(R.id.imgHospital);
        imgPolice = findViewById(R.id.imgPolice);

        // Service Section
        imgFoodWater = findViewById(R.id.imgFoodWater);
        imgShelter = findViewById(R.id.imgShelter);
        imgRescue = findViewById(R.id.imgRescue);
        imgMedical = findViewById(R.id.imgMedical);

        // Info Section
        imgSafetyTips = findViewById(R.id.imgSafetyTips);
        imgDisasterHelpline = findViewById(R.id.imgDisasterHelpline);
        imgFirstAidTips = findViewById(R.id.imgFirstAidTips);
        imgNGOVolunteer = findViewById(R.id.imgNGOVolunteer);

        // Actions
        emergencyButton.setOnClickListener(v -> showSOSNotification());
        notificationsButton.setOnClickListener(v -> openActivity(LoginActivity.class));
        reportingButton.setOnClickListener(v -> openActivity(LatestReportingActivity.class));
        menuButton.setOnClickListener(v -> openActivity(SettingsActivity.class));

        // Contact popups
        imgFireRescue.setOnClickListener(v -> showContactDialog("Fire Rescue", "101"));
        imgAmbulance.setOnClickListener(v -> showContactDialog("Ambulance", "102"));
        imgHospital.setOnClickListener(v -> showContactDialog("Hospital", "103"));
        imgPolice.setOnClickListener(v -> showContactDialog("Police", "100"));

        // Services → Login redirect
        imgFoodWater.setOnClickListener(v -> redirectToLoginWithToast());
        imgShelter.setOnClickListener(v -> redirectToLoginWithToast());
        imgRescue.setOnClickListener(v -> redirectToLoginWithToast());
        imgMedical.setOnClickListener(v -> redirectToLoginWithToast());

        // Info → Open Info Pages
        imgSafetyTips.setOnClickListener(v -> openActivity(SafetyTipsActivity.class));
        imgDisasterHelpline.setOnClickListener(v -> openActivity(helpline.class));
        imgFirstAidTips.setOnClickListener(v -> openActivity(FirstAidActivity.class));
        imgNGOVolunteer.setOnClickListener(v -> openActivity(NgoVolunteerActivity.class));
    }

    private void openActivity(Class<?> cls) {
        startActivity(new Intent(MainActivity.this, cls));
    }

    private void openInfoPage(Class<?> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        startActivity(intent);
    }

    private void showContactDialog(String title, String number) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage("Contact: " + number)
                .setPositiveButton("Close", null)
                .show();
    }

    private void redirectToLoginWithToast() {
        Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
        openActivity(LoginActivity.class);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Emergency SOS Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel for SOS alerts");

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private void showSOSNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_sos)
                .setContentTitle("🚨 Emergency Alert")
                .setContentText("Your emergency details have been sent!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build());
    }
}
