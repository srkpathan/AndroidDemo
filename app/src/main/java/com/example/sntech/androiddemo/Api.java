package com.example.sntech.androiddemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by sntech on 1/12/2018.
 */

public interface Api {

    String BASE_URL = "https://api.forismatic.com/api/1.0/";

    @GET("?method=getQuote&format=json&lang=en")
    Call<Quote> getQuote();
}
