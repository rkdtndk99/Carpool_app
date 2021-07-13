package com.example.everyClub.notice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.everyClub.MyClubActivity;
import com.example.everyClub.NoticeFragment;
import com.example.everyClub.R;
import com.example.everyClub.RetrofitInterface;
import com.example.everyClub.adapter.CommentViewAdapter;
import com.example.everyClub.data.Table;
import com.example.everyClub.data.Comment;
import com.example.everyClub.login.KakaoApplication;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//오빠 TableDetailActivity와 동일

public class NoticeDetailFragment extends Fragment {
    View view;
    TextView title_tv, content_tv, date_tv, name_tv;
    EditText comment_et;
    Button reg_button;
    ListView listView;
    Integer position;
    String table_id, name, tableOwner;
    ImageView update;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.249.18.120";

    // comment list
    public static List<Comment> comment_list;

    // comment adapter
    CommentViewAdapter commentAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        KakaoApplication ka = (KakaoApplication) getActivity().getApplicationContext();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.notice_detail, container, false);

        Bundle bundle = getArguments();
        table_id = bundle.getString("tableId");
        tableOwner = bundle.getString("tableOwner");
        name = bundle.getString("name");
        position = bundle.getInt("POSITION");


        title_tv = view.findViewById(R.id.title_tv);
        content_tv = view.findViewById(R.id.content_tv);
        name_tv = view.findViewById(R.id.name_tv);
        date_tv = view.findViewById(R.id.date_tv);
        listView = view.findViewById(R.id.listView);
        update = view.findViewById(R.id.update);

        Log.i("하하1","print"+tableOwner);
        Log.i("하하2","print"+name);
        if (!tableOwner.equals(name))
            update.setVisibility(View.GONE);

        comment_et = view.findViewById(R.id.comment_et);
        reg_button = view.findViewById(R.id.reg_button);


        // 게시판 정보 등록
        Table table = NoticeFragment.table_list.get(position);

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

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCreateComment(name);
            }
        });

        // listView 를 길게 눌렀을때 글 삭제
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l){
                Log.d("LONG CLICK", "OnLongClickListener");
                // 해당 게시물 정보
                Comment comment = comment_list.get(position);
                if (comment.getName().equals(name))
                    DeleteDialog(comment);
                return true;
            }
        });

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MyClubActivity)getActivity()).setFrag(0);  //NoticeFragment로 돌아가기
            }
        });

        view.findViewById(R.id.update).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("tableId", table.get_id());
                bundle.putString("name", name);
                Log.i("아이디1", "print"+table.get_id());
                KakaoApplication ka = (KakaoApplication) getActivity().getApplicationContext();
                ka.setUserName(name);
                ka.setTableId(table.get_id());
                ((MyClubActivity)getActivity()).setFrag(7);  //NoticeFragment로 돌아가기
            }
        });
        return view;
    }
    private void handleLoadComment() {
        HashMap<String, String> map = new HashMap<>();
        map.put("tableId", table_id);

        Call<List<Comment>> call = retrofitInterface.executeCommentLoad(map);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, retrofit2.Response<List<Comment>> response) {
                comment_list = response.body();
                commentAdapter = new CommentViewAdapter(getContext(), comment_list);
                listView.setAdapter(commentAdapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
            }
        });
    }

    private void handleCreateComment(String name){

        HashMap<String, String> map = new HashMap<>();

        map.put("tableId", table_id);
        map.put("name", name);
        map.put("content", comment_et.getText().toString());

        Call<Void> call = retrofitInterface.executeCommentCreate(map);

        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.code() == 200) {
                } else if (response.code() == 400) {
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
        comment_et.setText("");
        handleLoadComment();
    }

    private void DeleteDialog(Comment comment) {
        AlertDialog.Builder oDialog = new AlertDialog.Builder(getContext(),
                android.R.style.Theme_DeviceDefault_Light_Dialog);

        oDialog.setMessage("삭제하시겠습니까?")
                .setTitle("댓글 삭제")
                .setPositiveButton("아니오", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Log.i("Dialog", "취소");
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
        map.put("name", name);

        Call<Void> call = retrofitInterface.executeCommentDelete(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(getActivity().getApplicationContext(), "댓글 삭제 성공", Toast.LENGTH_SHORT);
                } else if (response.code() == 400) {
                    Toast.makeText(getActivity().getApplicationContext(), "내 댓글만 삭제 가능합니다.", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
        handleLoadComment();
    }
}
