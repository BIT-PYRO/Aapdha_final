package com.example.apadha_final;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class admin_reports extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReportsDatabaseHelper dbHelper;
    private ReportsAdapter reportsAdapter;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reports);

        back = findViewById(R.id.btnBack);
        recyclerView = findViewById(R.id.recyclerViewReports);  // RecyclerView for displaying reports

        // Initialize the database helper and RecyclerView
        dbHelper = new ReportsDatabaseHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch and display reports
        displayReports();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_reports.this, admin_main.class);
                startActivity(intent);
            }
        });
    }

    // Method to fetch and display reports in the RecyclerView
    private void displayReports() {
        Cursor cursor = dbHelper.getAllReports();  // Fetch all reports from the database
        if (cursor != null && cursor.getCount() > 0) {
            reportsAdapter = new ReportsAdapter(cursor);  // Pass the cursor to the adapter
            recyclerView.setAdapter(reportsAdapter);  // Set the adapter to RecyclerView
        } else {
            Toast.makeText(this, "No reports available", Toast.LENGTH_SHORT).show();
        }
    }
}
