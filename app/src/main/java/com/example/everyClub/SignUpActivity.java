package com.example.everyClub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

public class SignUpActivity extends AppCompatActivity {
    private ISessionCallback mSessionCallback;
    EditText signName, signId, signEmail, signPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
                        Toast.makeText(getApplicationContext(), "로그인 도중에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult)
                    {
                        // 세션이 닫힘..
                        Toast.makeText(getApplicationContext(), "세션이 닫혔습니다.. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result)
                    {
                        // 로그인 성공
                        signName = findViewById(R.id.signName);
                        signId = findViewById(R.id.signId);
                        signEmail = findViewById(R.id.signEmail);
                        signPassword = findViewById(R.id.signPassword);

                        signName.setText(result.getKakaoAccount().getProfile().getNickname());
                        signEmail.setText(result.getKakaoAccount().getEmail());


                        Toast.makeText(getApplicationContext(), "환영합니다 !", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception)
            {
                Toast.makeText(getApplicationContext(), "onSessionOpenFailed", Toast.LENGTH_SHORT).show();
            }
        };
//        Session.getCurrentSession().addCallback(mSessionCallback);
//        Session.getCurrentSession().checkAndImplicitOpen();
    }
}