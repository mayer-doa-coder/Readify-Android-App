package com.example.readify;

import android.net.Uri;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class BookModel {
    private String category;
    private String title;
    String description;
    private int imageResId;
    private String imageUrl;
    private List<String> authors;
    private float rating;
    private String pdfLocalPath;

    public BookModel(String title, int imageResId) {
        this.title = title;
        this.imageResId = imageResId;
        this.authors = new ArrayList<>();
    }

    public BookModel(String title, String description, int imageResId, String pdfLocalPath) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
        this.pdfLocalPath = pdfLocalPath;
    }

    public BookModel(String title,String description,int imageResId){
        this.title=title;
        this.description=description;
        this.imageResId=imageResId;
    }
    public String getDecodedPdfPath() {
        return pdfLocalPath != null ? Uri.decode(pdfLocalPath) : null;
    }

    public BookModel() {
    }

    public String getPdfFileName(){
        return title+".pdf";
    }

    public BookModel(String title, String description, String category, String imageUrl, List<String> authors, float rating) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
        this.authors = authors != null ? authors : new ArrayList<>();
        this.rating = rating;
    }

    public BookModel(String title, String description, String category, int imageResId, float rating) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageResId = imageResId;
        this.rating = rating;
    }

    public BookModel(String description, int imageResId, String pdfLocalPath, float rating, String title) {
        this.description=description;
        this.imageResId=imageResId;
        this.pdfLocalPath=pdfLocalPath;
        this.rating=rating;
        this.title=title;
    }




    public BookModel(JsonObject bookJson) {
        try {
            // Initialize authors list
            this.authors = new ArrayList<>();

            // Check if the JSON contains "volumeInfo"
            if (bookJson.has("volumeInfo")) {
                JsonObject volumeInfo = bookJson.getAsJsonObject("volumeInfo");

                // Parse title
                this.title = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "No Title Available";

                // Parse description
                this.description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No Description Available";

                // Parse authors
                if (volumeInfo.has("authors")) {
                    JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
                    for (int i = 0; i < authorsArray.size(); i++) {
                        this.authors.add(authorsArray.get(i).getAsString());
                    }
                } else {
                    this.authors.add("No Author Available");
                }

                // Parse category (first category, if multiple exist)
                if (volumeInfo.has("categories")) {
                    JsonArray categories = volumeInfo.getAsJsonArray("categories");
                    this.category = categories.size() > 0 ? categories.get(0).getAsString() : "No Category Available";
                } else {
                    this.category = "No Category Available";
                }

                // Parse average rating
                this.rating = volumeInfo.has("averageRating") ? volumeInfo.get("averageRating").getAsFloat() : 0.0f;

                // Parse image URL (thumbnail)
                if (volumeInfo.has("imageLinks")) {
                    JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
                    this.imageUrl = imageLinks.has("thumbnail") ? imageLinks.get("thumbnail").getAsString() : null;
                } else {
                    this.imageUrl = null; // No image available
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Provide fallback/default values for fields in case of parsing error
            this.title = "Error Parsing Title";
            this.description = "Error Parsing Description";
            this.category = "Unknown";
            this.authors = new ArrayList<>();
            this.authors.add("Unknown Author");
            this.imageUrl = null;
            this.rating = 0.0f;
        }
    }


    public String getCategory() {
        return category;
    }

    public String getPdfLocalPath() {
        return pdfLocalPath;
    }

    public void setPdfLocalPath(String pdfLocalPath) {
        this.pdfLocalPath = pdfLocalPath;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
