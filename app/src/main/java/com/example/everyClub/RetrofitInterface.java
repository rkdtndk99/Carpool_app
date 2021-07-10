package com.example.everyClub;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {


    @POST("/user/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/user/create")
    Call<Void> executeSignup (@Body HashMap<String, String> map);

    @POST("/table/create")
    Call<Void> executeTableCreate (@Body HashMap<String, String> map);

    @GET("/table/")
    Call<List<Table>> executeTableLoad ();

    @POST("/club/create")
    Call<Void> executeClubCreate (@Body HashMap<String, String> map);

}
