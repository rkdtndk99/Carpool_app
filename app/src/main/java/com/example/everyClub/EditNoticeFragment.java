package com.example.everyClub;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.everyClub.login.KakaoApplication;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditNoticeFragment extends Fragment {
    private View view;
    private String name, tableId;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.249.18.120";
    EditText edit_title, edit_content;

//    public EditNoticeFragment(String name, String _id;){
//        this.name = name;
//        this._id = _id;
//    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_notice, container, false);

        edit_title = view.findViewById(R.id.edit_title);
        edit_content = view.findViewById(R.id.edit_content);

//        Bundle bundle = getArguments();
//        tableId = bundle.getString("tableId");
//        name = bundle.getString("name")

        KakaoApplication ka = (KakaoApplication) getActivity().getApplicationContext();
        name = ka.getUserName();
        tableId = ka.getTableId();
        Log.i("아이디2", "print"+tableId);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        view.findViewById(R.id.edit_notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTableEdit();
                ((MyClubActivity)getActivity()).setFrag(0);  //자유게시판으로
            }
        });
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MyClubActivity)getActivity()).setFrag(6);
            }
        });
        return view;
    }
    private void handleTableEdit() {

        HashMap<String, String> map = new HashMap<>();
        map.put("tableId", tableId);
        map.put("name", name);
        map.put("title", edit_title.getText().toString());
        map.put("content", edit_content.getText().toString());


        Call<Void> call = retrofitInterface.executeTableEdit(map);

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
