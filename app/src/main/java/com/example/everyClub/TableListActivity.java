package com.example.everyClub;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.everyClub.adapter.TableAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;
import retrofit2.Response;


public class TableListActivity extends AppCompatActivity {

    // retrofit
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.249.18.120";

    // adapter
    TableAdapter tableAdapter;
    // table list
    public static List<Table> table_list;

    // 사용할 컴포넌트 선언
    ListView listView;
    Button reg_button;
    String name = "";

    // 리스트뷰에 사용할 제목 배열
    ArrayList<String> titleList = new ArrayList<>();

    // 클릭했을 때 어떤 게시물을 클릭했는지 게시물 번호를 담기 위한 배열
    ArrayList<String> seqList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_list);

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
        handleTableLoad();

        // listView 를 클릭했을 때 이벤트 추가
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // 어떤 값을 선택했는지 토스트를 뿌려줌
                Toast.makeText(TableListActivity.this, adapterView.getItemAtPosition(position) + " 클릭", Toast.LENGTH_SHORT).show();

                // 게시물의 번호와 userid를 가지고 DetailActivity 로 이동
                Intent intent = new Intent(TableListActivity.this, TableDetailActivity.class);
                intent.putExtra("POSITION", position);
                startActivity(intent);

            }
        });

        // 버튼 컴포넌트 초기화
        reg_button = findViewById(R.id.reg_button);

        // 버튼 이벤트 추가
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // userid 를 가지고 RegisterActivity 로 이동
                Intent intent = new Intent(TableListActivity.this, TableRegisterActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }

    private void handleTableLoad() {
        Call<List<Table>> call = retrofitInterface.executeTableLoad();

        call.enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {

                table_list = response.body();
                tableAdapter = new TableAdapter(getApplicationContext(), table_list);
                listView.setAdapter(tableAdapter);
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
                Toast.makeText(TableListActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}