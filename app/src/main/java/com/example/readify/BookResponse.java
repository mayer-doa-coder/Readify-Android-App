package com.example.readify;

import java.util.List;

public class BookResponse {
    private List<Item> items;

    public List<Item> getItems() { return items; }

    public static class Item {
        private VolumeInfo volumeInfo;

        public VolumeInfo getVolumeInfo() { return volumeInfo; }
    }

    public static class VolumeInfo {
        private String title;
        private List<String> authors;
        private String description;
        private List<String> categories;
        private ImageLinks imageLinks;

        public String getTitle() { return title; }
        public String getAuthors() { return authors != null ? String.join(", ", authors) : "Unknown Author"; }
        public String getDescription() { return description; }
        public String getCategory() { return (categories != null && categories.size() > 0) ? categories.get(0) : "N/A"; }
        public String getImageUrl() { return (imageLinks != null) ? imageLinks.getThumbnail() : null; }

        public static class ImageLinks {
            private String thumbnail;

            public String getThumbnail() { return thumbnail; }
        }
    }
}

