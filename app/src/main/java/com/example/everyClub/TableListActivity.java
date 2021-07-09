package com.example.everyClub;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TableListActivity extends AppCompatActivity {

    // 로그에 사용할 TAG 변수
    final private String TAG = getClass().getSimpleName();

    // 사용할 컴포넌트 선언
    ListView listView;
    Button reg_button;
    String userid = "";

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
        String name = intent.getStringExtra("name");
        nameText.setText(name);

        // LoginActivity 에서 넘긴 userid 값 받기
        userid = getIntent().getStringExtra("name");

        // 컴포넌트 초기화
        listView = findViewById(R.id.listView);

        // listView 를 클릭했을 때 이벤트 추가
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

// 어떤 값을 선택했는지 토스트를 뿌려줌
                Toast.makeText(TableListActivity.this, adapterView.getItemAtPosition(i) + " 클릭", Toast.LENGTH_SHORT).show();

// 게시물의 번호와 userid를 가지고 DetailActivity 로 이동
                Intent intent = new Intent(TableListActivity.this, TableDetailActivity.class);
                intent.putExtra("board_seq", seqList.get(i));
                intent.putExtra("userid", userid);
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
//                Intent intent = new Intent(TableListActivity.this, TableRegisterActivity.class);
//                intent.putExtra("userid", userid);
//                startActivity(intent);
            }
        });
    }

}