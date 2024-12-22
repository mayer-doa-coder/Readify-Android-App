package com.example.readify;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.BookViewHolder> {

    private Context context;
    private List<BookModel> bookList;

    public LibraryAdapter(Context context, List<BookModel> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookModel book = bookList.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.readButton.setOnClickListener(view -> {
            Log.d("ReadButton", "Read button clicked for: " + book.getTitle());
            Intent intent = new Intent(context, PDFViewerActivity.class);
            intent.putExtra("pdfFileName", book.getPdfFileName());
            context.startActivity(intent);
        });


        holder.bookImage.setImageResource(book.getImageResId());
        holder.bookDescription.setText(book.getDescription());

        holder.itemView.setOnClickListener(v -> {
            Uri pdfUri = Uri.parse(book.getPdfLocalPath());
            Intent intent = new Intent(Intent.ACTION_VIEW, pdfUri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setDataAndType(pdfUri, "application/pdf");
            context.startActivity(intent);
        });

        holder.addToFavoritesButton.setOnClickListener(v -> {
            if (!FavoriteManager.getInstance().isFavorite(book)) {
                FavoriteManager.getInstance().addFavorite(book);
                Toast.makeText(context, book.getTitle() + " added to favorites!", Toast.LENGTH_SHORT).show();
            } else {
                FavoriteManager.getInstance().removeFavorite(book);
                Toast.makeText(context, book.getTitle() + " removed from favorites!", Toast.LENGTH_SHORT).show();
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle, bookDescription;
        ImageView bookImage;
        Button addToFavoritesButton, readButton;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.library_book_title);
            bookDescription = itemView.findViewById(R.id.book_description);
            bookImage = itemView.findViewById(R.id.library_book_image);
            addToFavoritesButton = itemView.findViewById(R.id.add_to_favorites_button);
            readButton = itemView.findViewById(R.id.read_button);
        }
    }
    private void openBook(BookModel book) {
        String pdfPath = book.getPdfLocalPath();
        Uri uri = Uri.parse(pdfPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        context.startActivity(intent);
    }
}
