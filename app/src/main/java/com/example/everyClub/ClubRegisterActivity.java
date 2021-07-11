package com.example.everyClub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClubRegisterActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.249.18.120";

    // 사용할 컴포넌트 선언
    EditText title_et, content_et;
    Button reg_button;

    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_register);

        // ListActivity 에서 넘긴 userid 를 변수로 받음
        name = getIntent().getStringExtra("name");

        // 컴포넌트 초기화
        title_et = findViewById(R.id.title_et);
        content_et = findViewById(R.id.content_et);
        reg_button = findViewById(R.id.reg_button);

        // retrofit 등록
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        // 버튼 이벤트 추가
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 게시물 등록 함수
                handleClubCreate();
            }
        });
    }

    private void handleClubCreate() {

        HashMap<String, String> map = new HashMap<>();

        map.put("clubOwner", name);
        map.put("clubName", title_et.getText().toString());
        map.put("clubDesc", content_et.getText().toString());

        Call<Void> call = retrofitInterface.executeClubCreate(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {
                    Toast.makeText(ClubRegisterActivity.this,
                            "Club Create Successfully", Toast.LENGTH_LONG).show();
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(ClubRegisterActivity.this,
                            "Fail", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ClubRegisterActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}


