package com.example.everyClub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.everyClub.adapter.ListViewAdapter;
import com.example.everyClub.data.Table;
import com.example.everyClub.login.KakaoApplication;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyPostActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.249.18.120";
    ListViewAdapter tableAdapter;
    public static List<Table> table_list;
    ListView listView;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myposts);
        Log.i("주형1", "myTable");

        listView = (ListView) findViewById(R.id.my_posts_list);
        name = getIntent().getStringExtra("name");

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        handlemyTableLoad();

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPostActivity.this, LandingActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Table table = table_list.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("POSITION", position);
                bundle.putString("tableId", table.get_id());
                bundle.putString("name", name);
////                ((MyClubActivity)getActivity()).ndf.setArguments(bundle);
//                ((MyClubActivity)getActivity()).setFrag(6);

            }
        });
    }

    private void handlemyTableLoad() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        Log.i("이름", "print"+name);
        Call<List<Table>> call = retrofitInterface.executemyTableLoad(map);

        call.enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                table_list = response.body();
                tableAdapter = new ListViewAdapter(getApplicationContext(), table_list);
                listView.setAdapter(tableAdapter);
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
            }
        });
    }
}


