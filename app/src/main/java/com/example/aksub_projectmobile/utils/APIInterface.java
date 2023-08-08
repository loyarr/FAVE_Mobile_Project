package com.example.aksub_projectmobile.utils;

import com.example.aksub_projectmobile.models.TopQuoteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("stoic-quote")
    Call<TopQuoteResponse> getQuoteResponse(@Query("page") int currentPage);
}
