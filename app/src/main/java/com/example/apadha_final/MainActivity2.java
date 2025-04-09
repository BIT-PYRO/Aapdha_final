package com.example.apadha_final;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;



public class MainActivity2 extends AppCompatActivity {

    Button emergencyButton;
    ImageButton notificationsButton, menuButton;
    ImageView reportingButton;

    // Contact Icons
    ImageView imgFireRescue, imgAmbulance, imgHospital, imgPolice;

    // Service Icons
    ImageView imgFoodWater, imgShelter, imgRescue, imgMedical;

    // Info Icons
    ImageView imgSafetyTips, imgDisasterHelpline, imgFirstAidTips, imgNGOVolunteer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Core Views
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

        // Contact Popups
        imgFireRescue.setOnClickListener(v -> showContactDialog("Fire Rescue", "101"));
        imgAmbulance.setOnClickListener(v -> showContactDialog("Ambulance", "102"));
        imgHospital.setOnClickListener(v -> showContactDialog("Hospital", "103"));
        imgPolice.setOnClickListener(v -> showContactDialog("Police", "100"));

        // Info → PDFs
        imgSafetyTips.setOnClickListener(v -> openPDF("safety_tips.pdf"));
        imgDisasterHelpline.setOnClickListener(v -> openPDF("disaster_helpline.pdf"));
        imgFirstAidTips.setOnClickListener(v -> openPDF("first_aid.pdf"));
        imgNGOVolunteer.setOnClickListener(v -> openPDF("ngo_volunteer.pdf"));

        // Emergency Button → ReportActivity
        emergencyButton.setOnClickListener(view -> {
            startActivity(new Intent(this, ReportActivity.class));
        });

        // Reporting Banner → LatestReportingActivity
        reportingButton.setOnClickListener(view -> {
            startActivity(new Intent(this, LatestReportingActivity.class));
        });

        // Menu → SettingsActivity
        menuButton.setOnClickListener(view -> {
            startActivity(new Intent(this, SettingsActivity.class));
        });

        // Notifications Icon → LoginActivity
        notificationsButton.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        // Services → Redirect to unique activities
        imgFoodWater.setOnClickListener(v -> startActivity(new Intent(this, FoodWaterActivity.class)));
        imgShelter.setOnClickListener(v -> startActivity(new Intent(this, ShelterActivity.class)));
        imgRescue.setOnClickListener(v -> startActivity(new Intent(this, RescueActivity.class)));
        imgMedical.setOnClickListener(v -> startActivity(new Intent(this, MedicalActivity.class)));
    }

    private void showContactDialog(String title, String number) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage("Contact: " + number)
                .setPositiveButton("Close", null)
                .show();
    }

    private void openPDF(String fileName) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file:///android_asset/" + fileName), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No PDF viewer installed", Toast.LENGTH_SHORT).show();
        }
    }
}
