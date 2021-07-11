package com.example.everyClub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.everyClub.adapter.CommentAdapter;
import com.example.everyClub.adapter.TableAdapter;
import com.example.everyClub.data.Comment;
import com.example.everyClub.data.Table;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TableDetailActivity extends AppCompatActivity {

    // 사용할 컴포넌트 선언
    TextView title_tv, content_tv, date_tv, name_tv;
    EditText comment_et;
    Button reg_button;
    ListView listView;

    // retrofit
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.249.18.120";

    // comment list
    public static List<Comment> comment_list;

    // comment adapter
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_detail);

        // ListActivity 에서 넘긴 변수들을 받아줌
        int id = getIntent().getExtras().getInt("POSITION");

        // 컴포넌트 초기화
        title_tv = findViewById(R.id.title_tv);
        content_tv = findViewById(R.id.content_tv);
        name_tv = findViewById(R.id.name_tv);
        date_tv = findViewById(R.id.date_tv);
        listView = findViewById(R.id.listView);

        comment_et = findViewById(R.id.comment_et);
        reg_button = findViewById(R.id.reg_button);

        // 게시판 정보 등록
        Table table = TableListActivity.table_list.get(id);

        title_tv.setText(table.getTitle());
        name_tv.setText(table.getName());
        content_tv.setText(table.getContent());
        date_tv.setText(table.getUpdated());

        //retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        handleLoadComment();

        // 등록하기 버튼을 눌렀을 때 댓글 등록 함수 호출
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCreateComment();
            }
        });

        // listView 를 길게 눌렀을때 글 삭제
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l){
                Log.d("LONG CLICK", "OnLongClickListener");
                // 해당 게시물 정보
                Comment comment = comment_list.get(position);
                DeleteDialog(comment);
                return true;
            }
        });

    }

    private void handleLoadComment() {
        HashMap<String, String> map = new HashMap<>();
        map.put("tableId", getIntent().getExtras().getString("tableId"));

        Call<List<Comment>> call = retrofitInterface.executeCommentLoad(map);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                comment_list = response.body();
                commentAdapter = new CommentAdapter(getApplicationContext(), comment_list);
                listView.setAdapter(commentAdapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(TableDetailActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleCreateComment() {

        HashMap<String, String> map = new HashMap<>();

        map.put("tableId", getIntent().getExtras().getString("tableId"));
        map.put("name", getIntent().getExtras().getString("name"));
        map.put("content", comment_et.getText().toString());


        Call<Void> call = retrofitInterface.executeCommentCreate(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {
                    Toast.makeText(TableDetailActivity.this,
                            "Comment Create Successfully", Toast.LENGTH_LONG).show();
                } else if (response.code() == 400) {
                    Toast.makeText(TableDetailActivity.this,
                            "Fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TableDetailActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        handleLoadComment();
    }

    private void DeleteDialog(Comment comment) {
        AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
                android.R.style.Theme_DeviceDefault_Light_Dialog);

        oDialog.setMessage("삭제하시겠습니까?")
                .setTitle("댓글 삭제")
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
                        handleDeleteComment(comment);
                    }
                })
                .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                .show();
    }

    private void handleDeleteComment(Comment comment) {
        HashMap<String, String> map = new HashMap<>();
        map.put("commentId", comment.get_id());
        map.put("name", getIntent().getExtras().getString("name"));

        Call<Void> call = retrofitInterface.executeCommentDelete(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    comment_list.remove(comment);
                    Toast.makeText(TableDetailActivity.this,
                            "Comment Delete Successfully", Toast.LENGTH_LONG).show();
                    handleLoadComment();
                } else if (response.code() == 400) {
                    Toast.makeText(TableDetailActivity.this,
                            "Fail", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TableDetailActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
        commentAdapter.notifyDataSetChanged();
    }

}