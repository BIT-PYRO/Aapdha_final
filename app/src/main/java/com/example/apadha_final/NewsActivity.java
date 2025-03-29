package com.example.apadha_final;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        TextView newsContent = findViewById(R.id.newsContent);
        newsContent.setText("Live updates on disasters and emergency situations.");
    }
}
