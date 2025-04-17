package com.example.apadha_final;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;

public class ReportActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_VIDEO_CAPTURE = 102;

    private ImageView imagePreview;
    private VideoView videoPreview;
    private String selectedDisaster = "";
    private int pendingCameraAction = -1; // -1 = no action, 101 = photo, 102 = video
    private ReportsDatabaseHelper dbHelper;
    private Uri mediaUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        imagePreview = findViewById(R.id.photoPreview);
        videoPreview = findViewById(R.id.videoPreview);

        dbHelper = new ReportsDatabaseHelper(this);

        setupDisasterSelection();

        findViewById(R.id.photoButton).setOnClickListener(v -> {
            if (checkCameraPermission()) {
                openCamera(REQUEST_IMAGE_CAPTURE);
            } else {
                pendingCameraAction = REQUEST_IMAGE_CAPTURE;
                requestCameraPermission();
            }
        });

        findViewById(R.id.videoButton).setOnClickListener(v -> {
            if (checkCameraPermission()) {
                openCamera(REQUEST_VIDEO_CAPTURE);
            } else {
                pendingCameraAction = REQUEST_VIDEO_CAPTURE;
                requestCameraPermission();
            }
        });

        findViewById(R.id.reportButton).setOnClickListener(v -> {
            if (selectedDisaster.isEmpty()) {
                Toast.makeText(this, "Please select a disaster type", Toast.LENGTH_SHORT).show();
            } else {
                // Store the report in SQLite
                storeReportLocally();
            }
        });
    }

    private void setupDisasterSelection() {
        int[] disasterCardIds = {
                R.id.cardFlood, R.id.cardFire, R.id.cardTsunami, R.id.cardChemical,
                R.id.cardEarthquake, R.id.cardCyclone, R.id.cardDrought, R.id.cardLandslide
        };

        for (int id : disasterCardIds) {
            LinearLayout layout = findViewById(id);
            layout.setOnClickListener(view -> {
                for (int otherId : disasterCardIds) {
                    LinearLayout otherLayout = findViewById(otherId);
                    otherLayout.setBackgroundColor(Color.TRANSPARENT);
                }

                layout.setBackgroundColor(getResources().getColor(R.color.teal_200));
                if (layout.getChildCount() > 1 && layout.getChildAt(1) instanceof TextView) {
                    selectedDisaster = ((TextView) layout.getChildAt(1)).getText().toString();
                } else {
                    selectedDisaster = "Unknown";
                }
            });
        }
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                REQUEST_CAMERA_PERMISSION);
    }

    private void openCamera(int requestCode) {
        Intent intent;
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        } else {
            intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        }

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data.getExtras() != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imagePreview.setImageBitmap(photo);
                imagePreview.setVisibility(View.VISIBLE);
                videoPreview.setVisibility(View.GONE);
                mediaUri = getImageUri(photo);
            } else if (requestCode == REQUEST_VIDEO_CAPTURE && data.getData() != null) {
                Uri videoUri = data.getData();
                videoPreview.setVideoURI(videoUri);
                videoPreview.setVisibility(View.VISIBLE);
                videoPreview.start();
                imagePreview.setVisibility(View.GONE);
                mediaUri = videoUri;
            }
        }
    }

    private Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Report Image", null);
        return Uri.parse(path);
    }

    private void storeReportLocally() {
        if (mediaUri != null) {
            String mediaPath = mediaUri.toString();
            String userId = "user_id";  // Replace this with actual user ID from your app's authentication system
            long rowId = dbHelper.addReport(selectedDisaster, mediaPath, userId);

            if (rowId != -1) {
                Toast.makeText(this, "Report submitted successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to submit the report", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No media selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
                if (pendingCameraAction != -1) {
                    openCamera(pendingCameraAction);
                    pendingCameraAction = -1;
                }
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
                pendingCameraAction = -1;
            }
        }
    }
}
