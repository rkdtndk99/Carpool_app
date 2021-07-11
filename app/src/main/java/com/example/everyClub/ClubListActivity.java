package com.example.everyClub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.everyClub.adapter.ClubAdapter;
import com.example.everyClub.data.Club;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ClubListActivity extends AppCompatActivity {

    // retrofit
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.249.18.120";

    // adapter
    ClubAdapter clubAdapter;
    // club list
    public static List<Club> club_list;

    // 사용할 컴포넌트 선언
    ListView listView;
//    Button reg_button;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_list);

        // 유저 이름 가져왔는지 확인
        TextView nameText = (TextView) findViewById(R.id.nameText);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        nameText.setText(name);

        // 컴포넌트 초기화
        listView = findViewById(R.id.listView);

        // retrofit 등록
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        // 글 불러오기
        handleLoadClub();

        // listView 를 길게 눌렀을때 글 삭제
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l){
                Log.d("LONG CLICK", "OnLongClickListener");
                // 해당 게시물 정보
                Club club = club_list.get(position);
                DeleteDialog(club);
                return true;
            }
        });

        // listView 를 클릭했을 때 이벤트 추가
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // 해당 게시물 정보
                Club club = club_list.get(position);

                // 게시물의 번호와 userid를 가지고 DetailActivity 로 이동
                Intent intent = new Intent(ClubListActivity.this, ClubDetailActivity.class);
                intent.putExtra("POSITION", position);
                intent.putExtra("clubId", club.get_id());
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

//        // 버튼 컴포넌트 초기화
//        reg_button = findViewById(R.id.reg_button);
//
//        // 버튼 이벤트 추가
//        reg_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // userid 를 가지고 RegisterActivity 로 이동
//                Intent intent = new Intent(ClubListActivity.this, ClubRegisterActivity.class);
//                intent.putExtra("name", name);
//                startActivity(intent);
//                handleLoadClub();
//            }
//        });
    }

    private void DeleteDialog(Club club) {
        AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
                android.R.style.Theme_DeviceDefault_Light_Dialog);

        oDialog.setMessage("삭제하시겠습니까?")
                .setTitle("게시물 삭제")
                .setPositiveButton("아니오", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Log.i("Dialog", "취소");
                        Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton("예", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        handleDeleteclub(club);
                    }
                })
                .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                .show();
    }

    private void handleDeleteclub(Club club) {
        HashMap<String, String> map = new HashMap<>();
        map.put("clubId", club.get_id());
        map.put("clubOwner", name);

        Call<Void> call = retrofitInterface.executeClubDelete(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    club_list.remove(club);
                    Toast.makeText(ClubListActivity.this,
                            "club Delete Successfully", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    Toast.makeText(ClubListActivity.this,
                            "Fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ClubListActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        handleLoadClub();
    }

    public void handleLoadClub() {
        Call<List<Club>> call = retrofitInterface.executeClubLoad();

        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(Call<List<Club>> call, Response<List<Club>> response) {

                club_list = response.body();
                clubAdapter = new ClubAdapter(getApplicationContext(), club_list);
                listView.setAdapter(clubAdapter);
            }

            @Override
            public void onFailure(Call<List<Club>> call, Throwable t) {
                Toast.makeText(ClubListActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}