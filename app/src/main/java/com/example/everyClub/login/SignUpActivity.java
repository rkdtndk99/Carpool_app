package com.example.everyClub.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.everyClub.R;
import com.example.everyClub.RetrofitInterface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    public Retrofit retrofit;
    public RetrofitInterface retrofitInterface;
    public String BASE_URL = "http://192.249.18.120";
    EditText signName, signId, signEmail, signPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        ImageView btn_register = findViewById(R.id.btn_register);
        final EditText nameEdit = findViewById(R.id.signName);
        final EditText emailEdit = findViewById(R.id.signEmail);
        final EditText userIdEdit = findViewById(R.id.signId);
        final EditText passwordEdit = findViewById(R.id.signPassword);

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();

                map.put("name", nameEdit.getText().toString());
                map.put("email", emailEdit.getText().toString());
                map.put("userId", userIdEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());

                Log.i("아이디", userIdEdit.getText().toString());

                Call<Void> call = retrofitInterface.executeSignup(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Toast.makeText(getApplicationContext(),
                                    "Signed up successfully", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        } else if (response.code() == 400) {
                            Toast.makeText(getApplicationContext(),
                                    "Already registered", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

}