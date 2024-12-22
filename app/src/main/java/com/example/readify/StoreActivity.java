package com.example.readify;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        RecyclerView recyclerView = findViewById(R.id.store_recycler_view);

        List<BookModel> books = new ArrayList<>();
        books.add(new BookModel("The Great Gatsby", "A classic novel by F. Scott Fitzgerald.", R.drawable.gatsby_cover));
        books.add(new BookModel("To Kill a Mockingbird", "A novel by Harper Lee about racial inequality.", R.drawable.mockingbird_cover));
        books.add(new BookModel("1984", "A dystopian novel by George Orwell.", R.drawable.nineteen_eighty_four_cover));
        books.add(new BookModel("Pride and Prejudice","A timeless romance that explores manners, upbringing, and love.",R.drawable.pride_prejudice_cover));
        books.add(new BookModel("The Girl with the Dragon Tattoo","A gripping mystery involving corruption, family secrets, and crime.",R.drawable.dragon_tattoo_cover));
        books.add(new BookModel("The Hobbit", "An epic adventure of Bilbo Baggins through Middle-earth.", R.drawable.hobbit_cover ));

        StoreAdapter adapter = new StoreAdapter(this, books);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    private final ActivityResultLauncher<Intent> storagePermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    savePdfToStorage(uri);
                }
            });

    private void onDownloadButtonClick(String bookName) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, bookName + ".pdf");
        storagePermissionLauncher.launch(intent);
    }

    private void savePdfToStorage(Uri uri) {
        try (InputStream inputStream = getAssets().open("books/" + "The_Great_Gatsby.pdf");
             OutputStream outputStream = getContentResolver().openOutputStream(uri)) {

            if (outputStream == null) {
                Toast.makeText(this, "Error saving file", Toast.LENGTH_SHORT).show();
                return;
            }

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            Toast.makeText(this, "Download Complete", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: Unable to save file", Toast.LENGTH_SHORT).show();
        }
    }

}
