package com.example.readify;

import java.util.ArrayList;
import java.util.List;

public class FavoriteManager {
    private static FavoriteManager instance;
    private final List<BookModel> favoriteBooks;

    private FavoriteManager() {
        favoriteBooks = new ArrayList<>();
    }

    public static FavoriteManager getInstance() {
        if (instance == null) {
            instance = new FavoriteManager();
        }
        return instance;
    }

    public List<BookModel> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void addFavorite(BookModel book) {
        if (!favoriteBooks.contains(book)) {
            favoriteBooks.add(book);
        }
    }

    public void removeFavorite(BookModel book) {
        favoriteBooks.remove(book);
    }

    public boolean isFavorite(BookModel book) {
        return favoriteBooks.contains(book);
    }
}
