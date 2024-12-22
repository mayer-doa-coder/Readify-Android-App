package com.example.readify;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends DimActivity {

    private RecyclerView libraryRecyclerView;
    private LibraryAdapter adapter;
    private BookAdapter bookAdapter;
    private List<BookModel> bookList;
    Button readBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        bookList = new ArrayList<>();

        libraryRecyclerView = findViewById(R.id.book_list_recycler);
        libraryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference libraryRef = FirebaseDatabase.getInstance().getReference("Library");
        libraryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BookModel book = snapshot.getValue(BookModel.class);
                    bookList.add(book);
                }
                // Set up the RecyclerView
                adapter = new LibraryAdapter(LibraryActivity.this, bookList);
                bookAdapter=new BookAdapter(LibraryActivity.this,bookList);
                libraryRecyclerView.setLayoutManager(new LinearLayoutManager(LibraryActivity.this));
                libraryRecyclerView.setAdapter(bookAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LibraryActivity.this, "Failed to load books", Toast.LENGTH_SHORT).show();
            }
        });

        bookList.add(new BookModel("Book Title 1", "Description of Book 1", R.drawable.gatsby_cover));
        bookList.add(new BookModel("Book Title 2", "Description of Book 2", R.drawable.pride_prejudice_cover));

        adapter=new LibraryAdapter(this,bookList);
        bookAdapter = new BookAdapter(this, bookList);
        libraryRecyclerView.setAdapter(bookAdapter);

        loadBooksFromFirebase();


        readBtn=findViewById(R.id.readButton);
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, BookReaderActivity.class);
                startActivity(intent);
            }
        });


    }

    private void loadBooksFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Library");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookList.clear();
                for (DataSnapshot bookSnapshot : snapshot.getChildren()) {
                    BookModel book = bookSnapshot.getValue(BookModel.class);
                    if (book != null) {
                        // Decode the file path for each book
                        if (book.getPdfLocalPath() != null) {
                            book.setPdfLocalPath(Uri.decode(book.getPdfLocalPath()));
                        }
                        bookList.add(book);
                    }
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("LibraryActivity", "Error loading data: " + error.getMessage());
            }
        });
    }

    private File findPdfFile(Context context, String bookTitle) {
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (downloadsDir.exists() && downloadsDir.isDirectory()) {
            File[] files = downloadsDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().equalsIgnoreCase(bookTitle + ".pdf")) {
                        return file;
                    }
                }
            }
        }
        return null;
    }



    static class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

        private Context context;
        private List<BookModel> books;

        BookAdapter(Context context, List<BookModel> books) {
            this.context = context;
            this.books = books;
        }

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
            return new BookViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
            BookModel book = books.get(position);
            holder.bookTitle.setText(book.getTitle());
            holder.bookDescription.setText(book.getDescription());
            holder.bookImage.setImageResource(book.getImageResId());

            holder.readButton.setOnClickListener(v -> {
                Intent intent = new Intent(context, BookReaderActivity.class);
                intent.putExtra("bookTitle", book.getTitle());
                context.startActivity(intent);
            });

            holder.addToFavoritesButton.setOnClickListener(v ->
                    Toast.makeText(context, book.getTitle() + " added to favorites!", Toast.LENGTH_SHORT).show());
        }

        @Override
        public int getItemCount() {
            return books.size();
        }

        static class BookViewHolder extends RecyclerView.ViewHolder {

            TextView bookTitle, bookDescription;
            ImageView bookImage;
            Button readButton, addToFavoritesButton;

            BookViewHolder(@NonNull View itemView) {
                super(itemView);
                bookTitle = itemView.findViewById(R.id.book_title);
                bookDescription = itemView.findViewById(R.id.book_description);
                bookImage = itemView.findViewById(R.id.book_image);
                readButton = itemView.findViewById(R.id.read_button);
                addToFavoritesButton = itemView.findViewById(R.id.add_to_favorites_button);
            }
        }
    }
}
