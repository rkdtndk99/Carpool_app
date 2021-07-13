package com.example.everyClub.notice;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.everyClub.MyClubActivity;
import com.example.everyClub.R;
import com.example.everyClub.RetrofitInterface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewNoticeFragment extends Fragment {
    private View view;
    private String name;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.249.18.120";
    EditText notice_title, notice_content;

    public NewNoticeFragment(String name){
        this.name = name;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.new_notice, container, false);

        notice_title = view.findViewById(R.id.notice_title);
        notice_content = view.findViewById(R.id.notice_content);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        view.findViewById(R.id.write_notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTableCreate();
                ((MyClubActivity)getActivity()).setFrag(0);
            }
        });
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MyClubActivity)getActivity()).setFrag(0);
            }
        });
        return view;
    }
    private void handleTableCreate() {

        HashMap<String, String> map = new HashMap<>();

        map.put("name", name);
        map.put("title", notice_title.getText().toString());
        map.put("content", notice_content.getText().toString());

        Log.i("name", "handleTableCreate");

        Call<Void> call = retrofitInterface.executeTableCreate(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {

                } else if (response.code() == 400) {

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });

    }
}
