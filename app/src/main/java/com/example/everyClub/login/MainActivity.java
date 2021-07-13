package com.example.everyClub.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.everyClub.LandingActivity;
import com.example.everyClub.R;
import com.example.everyClub.RetrofitInterface;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.security.MessageDigest;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public ISessionCallback mSessionCallback;
    public Retrofit retrofit;
    public RetrofitInterface retrofitInterface;
    public String BASE_URL = "http://192.249.18.120";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        getAppKeyHash();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        ImageView loginBtn = findViewById(R.id.login);
        final EditText userIdEdit = findViewById(R.id.userIdEdit);
        final EditText passwordEdit = findViewById(R.id.passwordEdit);
        final TextView register = findViewById(R.id.register);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> map = new HashMap<>();

                map.put("userId", userIdEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());

                Call<User> call = retrofitInterface.executeLogin(map);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if (response.code() == 200) {
                            User user = response.body();
                            Intent intent = new Intent (getApplicationContext(), LandingActivity.class);
                            intent.putExtra("_userId", user.get_id());
                            intent.putExtra("name", user.getName());
                            startActivity(intent);

                        } else if (response.code() == 404) {
                            Toast.makeText(MainActivity.this, "로그인 안됨",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "로그인 에러",
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        //회원가입
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        //카카오로그인
        mSessionCallback = new ISessionCallback()
        {
            @Override
            public void onSessionOpened()
            {
                // 로그인 요청
                UserManagement.getInstance().me(new MeV2ResponseCallback()
                {

                    @Override
                    public void onFailure(ErrorResult errorResult)
                    {
                        // 로그인 실패
                        Toast.makeText(MainActivity.this, "로그인 도중에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult)
                    {
                        // 세션이 닫힘..
                        Toast.makeText(MainActivity.this, "세션이 닫혔습니다.. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result)
                    {
                        // 로그인 성공
                        Toast.makeText(MainActivity.this, "login success", Toast.LENGTH_SHORT).show();

                        HashMap<String, String> map = new HashMap<>();
                        map.put("name", result.getKakaoAccount().getProfile().getNickname());
                        map.put("email", result.getKakaoAccount().getEmail());


                        System.out.print(result.getId());

                        Call<Void> call = retrofitInterface.executeKLogin(map);

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                                if (response.code() == 200) {

                                    Intent intent = new Intent(MainActivity.this, LandingActivity.class);
                                    intent.putExtra("name", result.getKakaoAccount().getProfile().getNickname());
                                    intent.putExtra("email", result.getKakaoAccount().getEmail());
                                    intent.putExtra("profile_pic", result.getKakaoAccount().getProfile().getProfileImageUrl());
                                    intent.putExtra("birthday", result.getKakaoAccount().getBirthday());
                                    startActivity(intent);

                                } else if (response.code() == 400) {
                                    Toast.makeText(MainActivity.this, "Wrong Credentials",
                                            Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(MainActivity.this, t.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                        Log.i("id", result.getKakaoAccount().getProfile().getNickname());

                        Intent intent = new Intent(MainActivity.this, LandingActivity.class);
                        startActivity(intent);

                        Toast.makeText(MainActivity.this, "환영합니다!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception)
            {
                Toast.makeText(MainActivity.this, "onSessionOpenFailed", Toast.LENGTH_SHORT).show();
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);

        Session.getCurrentSession().addCallback(mSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

    }

    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.i("Hash key", something);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }
    }
}
