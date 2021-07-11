package com.example.everyClub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyClubActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    String name;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private NoticeFragment nf;
    private FreeFragment ff;
    private QuestionFragment qf;
    private PhotoFragment pf;
    private NewNoticeFragment nnf;
    private NewFreeFragment nff;
    private NewQuestionFragment nqf;
    NoticeDetailFragment ndf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myclub_page);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

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
                    case R.id.free:
                        setFrag(1);
                        break;
                    case R.id.questions:
                        setFrag(2);
                        break;
                    case R.id.photos:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });

        nf = new NoticeFragment(name);
        ff = new FreeFragment();
        qf = new QuestionFragment();
        pf = new PhotoFragment();
        nnf = new NewNoticeFragment(name);
        nff = new NewFreeFragment();
        nqf = new NewQuestionFragment();
        ndf = new NoticeDetailFragment();

        setFrag(0); // 첫 프래그먼트 화면 지정
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

            case 1:  //자유게시판
                ft.replace(R.id.Main_Frame,ff);
                ft.commit();
                break;

            case 2:  //질문게시판
                ft.replace(R.id.Main_Frame,qf);
                ft.commit();
                break;

            case 3:  //사진게시판
                ft.replace(R.id.Main_Frame, pf);
                ft.commit();
                break;

            case 4:  //글 작성하는 프래그먼트(공지사항)
                ft.replace(R.id.Main_Frame, nnf);
                ft.commit();
                break;

            case 5:  //글 작성하는 프래그먼트(자유)
                ft.replace(R.id.Main_Frame, nff);
                ft.commit();
                break;

            case 6:  //글 작성하는 프래그먼트(질문)
                ft.replace(R.id.Main_Frame, nqf);
                ft.commit();
                break;
            case 7:  //글 디테일 프래그먼트로 넘어가기
                ft.replace(R.id.Main_Frame, ndf);
                ft.commit();
                break;
        }
    }
}



