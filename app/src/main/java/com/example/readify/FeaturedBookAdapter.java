package com.example.readify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FeaturedBookAdapter extends RecyclerView.Adapter<FeaturedBookAdapter.FeaturedBookViewHolder>{
    private Context context;
    private List<BookModel> bookList;

    private List<Integer> bookImages;
    public FeaturedBookAdapter(List<Integer> bookImages) {
        this.bookImages = bookImages;
    }


    private OnBookClickListener onBookClickListener;

    public interface OnBookClickListener {
        void onBookClick(BookModel book);
    }

    public FeaturedBookAdapter(Context context, List<BookModel> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    public FeaturedBookAdapter(Context context, List<BookModel> bookList, OnBookClickListener listener) {
        this.context = context;
        this.bookList = bookList;
        this.onBookClickListener = listener;
    }

    public FeaturedBookAdapter(List<BookModel> bookList, OnBookClickListener listener) {
        this.bookList = bookList;
        this.onBookClickListener = listener;
    }

    @NonNull
    @Override
    public FeaturedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item_featured, parent, false);
        return new FeaturedBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedBookViewHolder holder, int position) {
        BookModel book = bookList.get(position);
        holder.bookCover.setImageResource(book.getImageResId());
        holder.bookTitle.setText(book.getTitle());
        holder.bookDescription.setText(book.getDescription());
        holder.bookRating.setRating(book.getRating());

        holder.bookCover.setOnClickListener(v -> onBookClickListener.onBookClick(book));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
//    public BookViewHolder
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_featured_book, parent, false);
//        }
//
//        BookModel book = books.get(position);
//
//        ImageView bookImage = convertView.findViewById(R.id.book_cover_image);
//        TextView bookTitle = convertView.findViewById(R.id.book_title_text);
//
//        bookImage.setImageResource(book.getImageResId());
//        bookTitle.setText(book.getTitle());
//
//        return convertView;
//    }

    public static class FeaturedBookViewHolder extends RecyclerView.ViewHolder {
        ImageView bookCover;
        TextView bookTitle, bookDescription;
        RatingBar bookRating;

        public FeaturedBookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.book_cover);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookDescription = itemView.findViewById(R.id.book_description);
            bookRating = itemView.findViewById(R.id.book_rating);
        }
    }

}
