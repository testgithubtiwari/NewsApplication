package com.example.newsapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitApi {

    @GET
    Call<NewsModal> getallNews(@Url String url);

    @GET
    Call<NewsModal> getallCategory(@Url String url);
}
