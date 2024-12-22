package com.example.readify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BookReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_reader);

        Button readButton1 = findViewById(R.id.readButton1);
        Button readButton2 = findViewById(R.id.readButton2);
        Button readButton3 = findViewById(R.id.readButton3);
        Button readButton4 = findViewById(R.id.readButton4);
        Button readButton5 = findViewById(R.id.readButton5);

        readButton1.setOnClickListener(v -> openPdf("The Great Gatsby.pdf"));
        readButton2.setOnClickListener(v -> openPdf("Pride and Prejudice.pdf"));
        readButton3.setOnClickListener(v -> openPdf("To Kill a Mockingbird.pdf"));
        readButton4.setOnClickListener(v -> openPdf("1984.pdf"));
        readButton5.setOnClickListener(v -> openPdf("The Girl with the Dragon Tattoo.pdf"));
    }
    private void openPdf(String fileName) {
        Intent intent = new Intent(BookReaderActivity.this, PDFViewerActivity.class);
        intent.putExtra("pdfFileName", fileName);
        startActivity(intent);
    }
}