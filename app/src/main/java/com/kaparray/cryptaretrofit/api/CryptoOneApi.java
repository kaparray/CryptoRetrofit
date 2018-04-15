package com.kaparray.cryptaretrofit.api;

import com.kaparray.cryptaretrofit.data.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CryptoOneApi {

    @GET("ticker/{id}/")
    Call<List<Data>> cryptaOne(@Path("id") String id);
}
