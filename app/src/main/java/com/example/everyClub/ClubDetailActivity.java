package com.example.everyClub;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.everyClub.data.Club;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClubDetailActivity extends AppCompatActivity {

    // 사용할 컴포넌트 선언
    TextView club_name, club_owner, club_desc;

    // retrofit
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.249.18.120";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_detail);

        // ListActivity 에서 넘긴 변수들을 받아줌
        int id = getIntent().getExtras().getInt("POSITION");

        // 컴포넌트 초기화
        club_name = (TextView) findViewById(R.id.club_name);
        club_owner = (TextView) findViewById(R.id.club_owner);
        club_desc = (TextView) findViewById(R.id.club_desc);



        // 게시판 정보 등록
        Club club = ClubListActivity.club_list.get(id);

        club_name.setText(club.getClubName());
        club_owner.setText(club.getClubOwner());
        club_desc.setText(club.getClubDesc());

        //retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }

}