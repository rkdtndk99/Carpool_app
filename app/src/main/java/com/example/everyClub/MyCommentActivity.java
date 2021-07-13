package com.example.everyClub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.everyClub.adapter.CommentViewAdapter;
import com.example.everyClub.data.Comment;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyCommentActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.249.18.120";
    CommentViewAdapter commentAdapter;
    public static List<Comment> comment_list;
    ListView listView;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycomments);

        Log.i("주형1", "myTable");

        listView = (ListView) findViewById(R.id.my_comment_list);
        name = getIntent().getStringExtra("name");

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        handlemyCommentLoad();

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCommentActivity.this, LandingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handlemyCommentLoad() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        Log.i("이름", "print"+name);
        Call<List<Comment>> call = retrofitInterface.executemyCommentLoad(map);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                comment_list = response.body();
                commentAdapter = new CommentViewAdapter(getApplicationContext(), comment_list);
                listView.setAdapter(commentAdapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
            }
        });
    }
}


