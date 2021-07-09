package com.example.everyClub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private ISessionCallback mSessionCallback;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.249.18.147";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        getAppKeyHash();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

//        Button loginBtn = findViewById(R.id.login);
//        final EditText userIdEdit = findViewById(R.id.userIdEdit);
//        final EditText passwordEdit = findViewById(R.id.passwordEdit);
//        final TextView signupText = findViewById(R.id.signup);
//
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                HashMap<String, String> map = new HashMap<>();
//
//                map.put("userId", userIdEdit.getText().toString());
//                map.put("password", passwordEdit.getText().toString());
//
//                Call<LoginResult> call = retrofitInterface.executeLogin(map);
//
//                call.enqueue(new Callback<LoginResult>() {
//                    @Override
//                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
//
//                        if (response.code() == 200) {
//
//                            LoginResult result = response.body();
//
//                            Intent intent = new Intent (getApplicationContext(), LandingActivity.class);
//                            intent.putExtra("name", result.getName());
//                            startActivity(intent);
//
//                        } else if (response.code() == 404) {
//                            Toast.makeText(MainActivity.this, "Wrong Credentials",
//                                    Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<LoginResult> call, Throwable t) {
//                        Toast.makeText(MainActivity.this, t.getMessage(),
//                                Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//        });

//회원가입
//        signupText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
//                startActivity(intent);
//            }
//        });

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
                        map.put("userId", Long.toString(result.getId()));

                        System.out.print(result.getId());

                        Call<Void> call = retrofitInterface.executeLogin(map);

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                                if (response.code() == 200) {

                                    Intent intent = new Intent (getApplicationContext(), LandingActivity.class);
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
                        intent.putExtra("name", result.getKakaoAccount().getProfile().getNickname());
                        intent.putExtra("profileImg", result.getKakaoAccount().getProfile().getProfileImageUrl());
                        intent.putExtra("email", result.getKakaoAccount().getEmail());
                        startActivity(intent);

//                        Toast.makeText(MainActivity.this, "환영 합니다 !", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception)
            {
                Toast.makeText(MainActivity.this, "onSessionOpenFailed", Toast.LENGTH_SHORT).show();
            }
        };
        Session.getCurrentSession().addCallback(mSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

    }

//    private void handleSignupDialog() {
//
//        View view = getLayoutInflater().inflate(R.layout.signup_dialog, null);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(view).show();
//
//        Button signupBtn = view.findViewById(R.id.signup);
//        final EditText nameEdit = view.findViewById(R.id.nameEdit);
//        final EditText userIdEdit = view.findViewById(R.id.userIdEdit);
//        final EditText passwordEdit = view.findViewById(R.id.passwordEdit);
//
//        signupBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                HashMap<String, String> map = new HashMap<>();
//
//                map.put("name", nameEdit.getText().toString());
//                map.put("userId", userIdEdit.getText().toString());
//                map.put("password", passwordEdit.getText().toString());
//
//                Call<Void> call = retrofitInterface.executeSignup(map);
//
//                call.enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//
//                        if (response.code() == 200) {
//                            Toast.makeText(MainActivity.this,
//                                    "Signed up successfully", Toast.LENGTH_LONG).show();
//                        } else if (response.code() == 400) {
//                            Toast.makeText(MainActivity.this,
//                                    "Already registered", Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//                        Toast.makeText(MainActivity.this, t.getMessage(),
//                                Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//        });
//
//    }


//    private void getAppKeyHash() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String something = new String(Base64.encode(md.digest(), 0));
//                Log.e("Hash key", something);
//            }
//        } catch (Exception e) {
//            Log.e("name not found", e.toString());
//        }
//    }
}
