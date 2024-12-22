package com.example.readify;

import android.app.DownloadManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.BookViewHolder> {

    private Context context;
    private List<BookModel> bookList;

    public StoreAdapter(Context context, List<BookModel> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book_store, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookModel book = bookList.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.bookDescription.setText(book.getDescription());
        holder.bookCoverImage.setImageResource(book.getImageResId());


        holder.downloadButton.setOnClickListener(v -> {
            String pdfFileName=book.getTitle()+".pdf";
            downloadBookFromAssets(pdfFileName, context,book);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle, bookDescription;
        ImageView bookCoverImage;
        Button downloadButton;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookDescription = itemView.findViewById(R.id.book_description);
            bookCoverImage = itemView.findViewById(R.id.book_cover_image);
            downloadButton = itemView.findViewById(R.id.download_button);
        }
    }

    public void downloadBookFromAssets(String pdfFileName, Context context,BookModel book) {
        AssetManager assetManager = context.getAssets();
        File destinationFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), pdfFileName);

        try {
            InputStream in = assetManager.open(pdfFileName);
            OutputStream out = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
            book.setPdfLocalPath(destinationFile.getAbsolutePath());

            Toast.makeText(context, "Book downloaded to Downloads folder", Toast.LENGTH_SHORT).show();

            addToLibrary(book);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToLibrary(BookModel book) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Library");
        String bookId = databaseReference.push().getKey();
        book.setPdfLocalPath(Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), book.getTitle() + ".pdf")).toString());
        assert bookId != null;
        databaseReference.child(bookId).setValue(book);
        Toast.makeText(context, "Book added to library", Toast.LENGTH_SHORT).show();
    }
}

