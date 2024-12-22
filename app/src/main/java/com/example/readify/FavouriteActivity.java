package com.example.readify;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends DimActivity {

    private RecyclerView recyclerView;
    private LibraryActivity.BookAdapter bookAdapter;
    public static List<BookModel> favoriteBooks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favourite);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.favorites_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<BookModel> favoriteBooks = FavoriteManager.getInstance().getFavoriteBooks();
        LibraryAdapter adapter = new LibraryAdapter(this, favoriteBooks);
        recyclerView.setAdapter(adapter);

        if (favoriteBooks.isEmpty()) {
            Toast.makeText(this, "No favorite books yet!", Toast.LENGTH_SHORT).show();
        }
    }
}