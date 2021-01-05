package com.aloneliontech.cartnew;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Appinterface {

    String BaseUrl = "https://script.google.com/macros/s/AKfycbyiocV9hLKIug2hkJKm_5FKnbXmp4z_suxQCrzlejqsjlxIni4/";
    @POST("exec")
    Call<google>  google(@Body RequestBody requestBody);
}
