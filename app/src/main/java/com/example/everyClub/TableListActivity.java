package com.example.everyClub;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.everyClub.adapter.TableAdapter;
import com.example.everyClub.data.Table;

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

        // 글 새로고침
//        tableAdapter.notifyDataSetChanged();
        // 글 불러오기
        handleTableLoad();

        Context this_context = getApplicationContext();


        // listView 를 길게 눌렀을때 글 삭제
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l){
                Log.d("LONG CLICK", "OnLongClickListener");
                // 해당 게시물 정보
                Table table = table_list.get(position);
                DeleteDialog(table);
                return true;
            }
        });

        // listView 를 클릭했을 때 이벤트 추가
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // 해당 게시물 정보
                Table table = table_list.get(position);

                // 게시물의 번호와 userid를 가지고 DetailActivity 로 이동
                Intent intent = new Intent(TableListActivity.this, TableDetailActivity.class);
                intent.putExtra("POSITION", position);
                intent.putExtra("tableId", table.get_id());
                intent.putExtra("name", name);
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

    private void DeleteDialog(Table table) {
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
                        handleDeleteTable(table);
                    }
                })
                .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                .show();
    }

    private void handleDeleteTable(Table table) {
        HashMap<String, String> map = new HashMap<>();
        map.put("tableId", table.get_id());
        map.put("name", name);

        Call<Void> call = retrofitInterface.executeTableDelete(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
//                table_list.remove(table);
                if (response.code() == 200) {
                    Toast.makeText(TableListActivity.this,
                            "Table Delete Successfully", Toast.LENGTH_LONG).show();
                } else if (response.code() == 400) {
                    Toast.makeText(TableListActivity.this,
                            "Fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TableListActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

//        tableAdapter.notifyDataSetChanged();
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