package com.example.everyClub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.everyClub.login.KakaoApplication;
import com.example.everyClub.notice.NewNoticeFragment;
import com.example.everyClub.notice.NoticeDetailFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyClubActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    String _userId, name,pic_uri,birthday, email;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ProfileFragment pff;
    private NoticeFragment nf;
    private MessageFragment mf;
    private NewNoticeFragment nnf;
    NoticeDetailFragment ndf;
    ChatFragment cf;
    EditNoticeFragment ef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myclub_page);

        Intent intent = getIntent();
        _userId = intent.getStringExtra("_userId");
        name = intent.getStringExtra("name");
        pic_uri = intent.getStringExtra("pic_uri");
        birthday =intent.getStringExtra("birthday");
        email = intent.getStringExtra("email");

        KakaoApplication ka = (KakaoApplication) getApplicationContext();
        ka.setUserName(name);

        Log.i("name", "print" + name);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.notice:
                        setFrag(0);
                        break;
                    case R.id.profile:
                        setFrag(1);
                        break;
                    case R.id.message:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });
        pff = new ProfileFragment(name,pic_uri, birthday, email);
        nf = new NoticeFragment(_userId, name);
        mf = new MessageFragment();
        nnf = new NewNoticeFragment(name);
        ndf = new NoticeDetailFragment();
        cf = new ChatFragment();
        ef = new EditNoticeFragment();

        setFrag(1); // 첫 프래그먼트 화면 지정
    }

    // 프레그먼트 교체
    public void setFrag(int n)
    {
        fm = getSupportFragmentManager();
        ft= fm.beginTransaction();
        switch (n)
        {
            case 0:  //공지게시판
                ft.replace(R.id.Main_Frame,nf);
                ft.commit();
                break;

            case 1:  //프로필
                ft.replace(R.id.Main_Frame,pff);
                ft.commit();
                break;

            case 3:  //단체 메신저
                ft.replace(R.id.Main_Frame,mf);
                ft.commit();
                break;

            case 4:  //글 작성하는 프래그먼트(공지사항)
                ft.replace(R.id.Main_Frame, nnf);
                ft.commit();
                break;

            case 5:  //채팅 프래그먼트로 넘어가기
                ft.replace(R.id.Main_Frame, cf);
                ft.commit();
                break;

            case 6:  //글 디테일 프래그먼트로 넘어가기
                ft.replace(R.id.Main_Frame, ndf);
                ft.commit();
                break;

            case 7:  //글 수정 프래그먼트로 넘어가기
                ft.replace(R.id.Main_Frame, ef);
                ft.commit();
                break;
        }
    }
}