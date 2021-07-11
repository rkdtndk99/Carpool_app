package com.example.everyClub;

import com.example.everyClub.data.Table;
import com.example.everyClub.login.LoginResult;
import com.example.everyClub.data.Comment;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface RetrofitInterface {


    @POST("/user/kakao_create")
    Call<Void> executeKLogin(@Body HashMap<String, String> map);

    @POST("/user/create")
    Call<Void> executeSignup (@Body HashMap<String, String> map);

    @POST("/user/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/table/create")
    Call<Void> executeTableCreate (@Body HashMap<String, String> map);

    @GET("/table/")
    Call<List<Table>> executeTableLoad ();

    @POST("/club/create")
    Call<Void> executeClubCreate (@Body HashMap<String, String> map);

    @HTTP(method = "DELETE", path = "/table/delete", hasBody = true)
    Call<Void> executeTableDelete (@Body HashMap<String, String> map);

    @POST("/comment/create")
    Call<Void> executeCommentCreate (@Body HashMap<String, String> map);

    @POST("/comment/")
    Call<List<Comment>> executeCommentLoad (@Body HashMap<String, String> map);

    @HTTP(method = "DELETE", path = "/comment/delete", hasBody = true)
    Call<Void> executeCommentDelete (@Body HashMap<String, String> map);
}
