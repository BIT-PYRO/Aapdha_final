package com.example.apadha_final;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PdfViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String fileName = getIntent().getStringExtra("pdfFileName");

        if (fileName != null && !fileName.isEmpty()) {
            openPdfFromAssets(fileName);
        } else {
            Toast.makeText(this, "No PDF file specified", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void openPdfFromAssets(String fileName) {
        try {
            // Copy the file from assets to cache
            File outFile = new File(getCacheDir(), fileName);
            if (!outFile.exists()) {
                InputStream is = getAssets().open(fileName);
                FileOutputStream fos = new FileOutputStream(outFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }

                fos.flush();
                fos.close();
                is.close();
            }

            // Open using external PDF viewer
            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", outFile);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(intent);
        } catch (IOException e) {
            Toast.makeText(this, "Failed to open PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(this, "No PDF viewer found!", Toast.LENGTH_LONG).show();
        }
    }
}
