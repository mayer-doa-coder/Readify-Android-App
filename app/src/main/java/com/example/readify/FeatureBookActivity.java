package com.example.readify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeatureBookActivity extends DimActivity {

    private TextView titleView, authorView, descriptionView, categoryView;
    private ImageView coverImageView;


    private Retrofit retrofit;
    private BookApi apiService;


    private RecyclerView recyclerView;
    private FeaturedBookAdapter adapter;
    private FeaturedBookAdapter featuredBookAdapter;
    private List<BookModel> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feature_book);

        titleView = findViewById(R.id.book_details_title);
        authorView = findViewById(R.id.book_details_author);
        descriptionView = findViewById(R.id.book_details_description);
        categoryView = findViewById(R.id.book_details_category);
        recyclerView = findViewById(R.id.feature_book_recycler_view);

        bookList = new ArrayList<>();
        populateBooks();
        //adapter = new FeaturedBookAdapter(this, bookList);
        featuredBookAdapter = new FeaturedBookAdapter(this, bookList, this::fetchBookDetails);
        //adapter = new FeaturedBookAdapter(bookList, this::fetchBookDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(featuredBookAdapter);


    }
    private void populateBooks() {
        bookList.add(new BookModel("The Great Gatsby","A tragic story of love, wealth, and the American dream.","Fiction",R.drawable.gatsby_cover,4.5f));
        bookList.add(new BookModel("To Kill a Mockingbird","A profound tale of racial injustice and moral growth.","Classic",R.drawable.mockingbird_cover,4.8f));
        bookList.add(new BookModel("1984","A dystopian novel depicting a totalitarian regime and surveillance society." ,"Science Fiction",R.drawable.nineteen_eighty_four_cover,4.0f));
        bookList.add(new BookModel("Pride and Prejudice","A timeless romance that explores manners, upbringing, and love.","Romance" ,R.drawable.pride_prejudice_cover,4.0f));
        bookList.add(new BookModel("The Girl with the Dragon Tattoo","A gripping mystery involving corruption, family secrets, and crime.","Thriller" ,R.drawable.dragon_tattoo_cover,4.5f));
        bookList.add(new BookModel("Jaws", "A thrilling tale of terror as a great white shark preys on a small beach town.", "Thriller", R.drawable.jaws, 4.3f));
        bookList.add(new BookModel("The Hobbit", "An epic adventure of Bilbo Baggins through Middle-earth.", "Fantasy", R.drawable.hobbit_cover, 4.8f));
    }

    private void fetchBookDetails(BookModel book) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/books/") // Google Books API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(BookApi.class);

        // Use "intitle:" to search for books by title
        apiService.getBookDetails("intitle:" + book.getTitle()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject jsonObject = response.body();
                    JsonArray itemsArray = jsonObject.getAsJsonArray("items");

                    if (itemsArray != null && itemsArray.size() > 0) {
                        JsonObject bookJson = itemsArray.get(0).getAsJsonObject(); // Get the first book
                        BookModel fetchedBook = new BookModel(bookJson);
                        updateUI(fetchedBook); // Update the UI with fetched book details
                    } else {
                        Toast.makeText(FeatureBookActivity.this, "No books found!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FeatureBookActivity.this, "Failed to fetch details!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(FeatureBookActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateUI(BookModel book) {
        if (book != null) {
            TextView titleTextView = findViewById(R.id.book_details_title);
            TextView categoryTextView = findViewById(R.id.book_details_category);
            TextView descriptionTextView = findViewById(R.id.book_details_description);
            TextView authorsTextView = findViewById(R.id.book_details_author);



            titleTextView.setText(book.getTitle());
            categoryTextView.setText(book.getCategory());
            descriptionTextView.setText(book.getDescription());
        }
    }
}