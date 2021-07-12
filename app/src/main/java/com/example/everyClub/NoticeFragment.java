package com.example.everyClub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.everyClub.adapter.ListViewAdapter;
import com.example.everyClub.data.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//종서오빠 TableListActivity와 동일

public class NoticeFragment extends Fragment {
    private View view;
    private String name;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.249.18.120";
    ListViewAdapter tableAdapter;
    public static List<Table> table_list;
    ListView listView;
    private FragmentManager fm;
    private FragmentTransaction ft;

    // 리스트뷰에 사용할 제목 배열
    ArrayList<String> titleList = new ArrayList<>();

    // 클릭했을 때 어떤 게시물을 클릭했는지 게시물 번호를 담기 위한 배열
    ArrayList<String> seqList = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    public NoticeFragment(String name){
        this.name=name;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notice, container, false);
        listView = (ListView) view.findViewById(R.id.notice_listview);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        handleTableLoad();

        //게시물 내용 자세히 보여주는 프래그먼트로 이동
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // 어떤 값을 선택했는지 토스트를 뿌려줌
                Toast.makeText(getActivity(), adapterView.getItemAtPosition(position) + " 클릭", Toast.LENGTH_SHORT).show();

                Table table = table_list.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("POSITION", position);
                bundle.putString("tableId", table.get_id());
                bundle.putString("name", name);


                ((MyClubActivity)getActivity()).ndf.setArguments(bundle);
                ((MyClubActivity)getActivity()).setFrag(6);
            }
        });
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


        view.findViewById(R.id.btn_new_notice).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ((MyClubActivity)getActivity()).setFrag(4);
           }
        });

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LandingActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void handleTableLoad() {
        Call<List<Table>> call = retrofitInterface.executeTableLoad();

        call.enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                table_list = response.body();
                tableAdapter = new ListViewAdapter(getContext(), table_list);
                listView.setAdapter(tableAdapter);
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
            }
        });
    }

    private void DeleteDialog(Table table) {
        AlertDialog.Builder oDialog = new AlertDialog.Builder(getContext(),
                android.R.style.Theme_DeviceDefault_Light_Dialog);

        oDialog.setMessage("삭제하시겠습니까?")
                .setTitle("게시물 삭제")
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
                } else if (response.code() == 400) {
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });

//        tableAdapter.notifyDataSetChanged();
    }

}