package com.example.readify;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookApi {
    @GET("v1/volumes")
    Call<JsonObject> getBookDetails(@Query("q") String query);
}

