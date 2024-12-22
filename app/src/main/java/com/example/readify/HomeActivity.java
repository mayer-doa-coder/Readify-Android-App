package com.example.readify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    static final String PREFS_NAME = "AppPrefs";
    static final String DIM_MODE_KEY = "DimMode";
    private View dimOverlay;
    private TextView featureBookButton;
    private RecyclerView featuredBooksRecyclerView;
    private FeaturedBookAdapter adapter;
    private List<BookModel> bookList;
    ImageView bookImage1,bookImage2,bookImage3;
    Button categoryButton;
    Button favoritesButton;
    Button openButton;
    Button storeButton;
    Button bookMark;

    ImageButton person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isDimMode = preferences.getBoolean(DIM_MODE_KEY, false);
        dimOverlay = findViewById(R.id.dim_overlay);
        updateDimMode(isDimMode);
        Switch dimModeSwitch = findViewById(R.id.switch_dim_mode);
        dimModeSwitch.setChecked(isDimMode);
        dimModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(DIM_MODE_KEY, isChecked);
                editor.apply();
                updateDimMode(isChecked);
                Intent intent = new Intent("ACTION_UPDATE_DIM_MODE");
                sendBroadcast(intent);
            }
        });

        LinearLayout parentLayout = findViewById(R.id.home_parent_layout);

        bookMark=findViewById(R.id.bookmark_button);
        storeButton=findViewById(R.id.store_button);
        featureBookButton=findViewById(R.id.featured_title);
        favoritesButton=findViewById(R.id.favorite_books_button);
        openButton=findViewById(R.id.open_button);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        featuredBooksRecyclerView.setLayoutManager(layoutManager);
        categoryButton=findViewById(R.id.add_catagory_button);

        bookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, BookReaderActivity.class);
                startActivity(intent);
            }
        });

        person=findViewById(R.id.person_button);
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, CategoryDashboardActivity.class);
                startActivity(intent);
            }
        });

        ImageView libraryImage=findViewById(R.id.library_book_image);


        ImageView bookImage1 = findViewById(R.id.book_image_1);
        ImageView bookImage2 = findViewById(R.id.book_image_2);
        ImageView bookImage3 = findViewById(R.id.book_image_3);

        LinearLayout featuredBooksLayout = findViewById(R.id.featured_books_layout);

        int[] bookImages={
                R.drawable.pride_prejudice_cover,
                R.drawable.gatsby_cover,
                R.drawable.catcher_rye_cover
        };

        bookImage1.setImageResource(bookImages[0]);
        bookImage2.setImageResource(bookImages[1]);
        bookImage3.setImageResource(bookImages[2]);

        libraryImage.setImageResource(R.drawable.cartoon);



//        List<Integer> bookImages = new ArrayList<>();
//        bookImages.add(R.drawable.gatsby_cover); // Replace with your actual drawable resources
//        bookImages.add(R.drawable.pride_prejudice_cover);
//        bookImages.add(R.drawable.nineteen_eighty_four_cover);

//        adapter=new FeaturedBookAdapter(bookImages);
//        featuredBooksRecyclerView.setAdapter(adapter);



        featureBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,FeatureBookActivity.class);
                startActivity(intent);
            }
        });

        favoritesButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FavouriteActivity.class);
            startActivity(intent);
        });

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,LibraryActivity.class);
                startActivity(intent);
            }
        });

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,StoreActivity.class);
                startActivity(intent);
            }
        });


    }
    private void updateDimMode(boolean enableDimMode) {
        if (enableDimMode) {
            dimOverlay.setVisibility(View.VISIBLE);
        } else {
            dimOverlay.setVisibility(View.GONE);
        }
    }
}