package com.example.readify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.readify.databinding.ActivityPdfAddBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class PdfAddActivity extends DimActivity {

    private ActivityPdfAddBinding binding;
    private FirebaseAuth firebaseAuth;

    private static final int PDF_PICK_CODE = 1000;
    private Uri pdfUri = null;
    private File savedFile;

    private static final String TAG = "ADD_PDF_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityPdfAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        // On click listener for the attach button to pick a PDF file
        binding.attachBtn.setOnClickListener(v -> pdfPickIntent());

        // On click listener for the submit button to validate the data and save PDF
        binding.submitBtn.setOnClickListener(v -> validateData());
    }

    // Variables to hold the title and description of the PDF
    private String title = "", description = "";

    // Validate the data before proceeding with PDF upload
    private void validateData() {
        Log.d(TAG, "validateData: validating data...");
        title = binding.titleEt.getText().toString().trim();
        description = binding.descriptionEt.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Enter Description", Toast.LENGTH_SHORT).show();
        } else if (pdfUri == null) {
            Toast.makeText(this, "Pick Pdf", Toast.LENGTH_SHORT).show();
        } else {
            // Proceed with saving the PDF locally and uploading it to Firebase
            savePdfLocally();
        }
    }

    // Save PDF file locally to the app's internal storage
    private void savePdfLocally() {
        try {
            // Save file to app's private directory
            InputStream inputStream = getContentResolver().openInputStream(pdfUri);
            File dir = new File(getFilesDir(), "pdfs");
            if (!dir.exists()) dir.mkdirs();

            savedFile = new File(dir, title + ".pdf");
            FileOutputStream outputStream = new FileOutputStream(savedFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

            Log.d(TAG, "savePdfLocally: PDF saved locally");
            sharePdfUri();  // Generate a shareable URI for the saved PDF file

        } catch (Exception e) {
            Log.e(TAG, "savePdfLocally: Failed to save PDF", e);
            Toast.makeText(this, "Failed to save PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Generate a URI for sharing the PDF file
    private void sharePdfUri() {
        // Get shareable URI using FileProvider
        Uri fileUri = FileProvider.getUriForFile(
                this,
                getPackageName() + ".fileprovider",
                savedFile
        );

        Log.d(TAG, "sharePdfUri: Generated URI: " + fileUri);
        savePdfInfoToDb(fileUri.toString());  // Save the PDF info and the URI to Firebase
    }

    // Save PDF info and the generated URI to Firebase Realtime Database
    private void savePdfInfoToDb(String pdfUrl) {
        Log.d(TAG, "savePdfInfoToDb: saving PDF info to Firebase...");
        String uid = firebaseAuth.getUid();
        long timestamp = System.currentTimeMillis();

        HashMap<String, Object> pdfData = new HashMap<>();
        pdfData.put("uid", uid);
        pdfData.put("id", String.valueOf(timestamp));
        pdfData.put("title", title);
        pdfData.put("description", description);
        pdfData.put("url", pdfUrl);
        pdfData.put("timestamp", timestamp);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(String.valueOf(timestamp))
                .setValue(pdfData)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(PdfAddActivity.this, "PDF info saved successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(PdfAddActivity.this, "Failed to save PDF info: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    private void pdfPickIntent() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PDF_PICK_CODE) {
            pdfUri = data.getData();
            Log.d(TAG, "onActivityResult: PDF Picked: " + pdfUri);
        }
    }

    private Uri savePdfToInternalStorage(Uri sourceUri, String fileName) {
        try {
            File pdfsDir = new File(getFilesDir(), "pdfs");
            if (!pdfsDir.exists()) {
                pdfsDir.mkdirs();
            }

            File destinationFile = new File(pdfsDir, fileName);

            InputStream inputStream = getContentResolver().openInputStream(sourceUri);
            OutputStream outputStream = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            return FileProvider.getUriForFile(this, "com.example.readify.fileprovider", destinationFile);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save PDF locally", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
