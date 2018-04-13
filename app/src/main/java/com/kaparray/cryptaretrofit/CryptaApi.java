package com.kaparray.cryptaretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CryptaApi {

    @GET("ticker/?convert=EUR")
    Call<List<Data>> cryptaList(@Query("start") String start, @Query("limit") String limit);
}
