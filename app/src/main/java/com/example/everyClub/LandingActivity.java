package com.example.everyClub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LandingActivity extends AppCompatActivity {
    String _userId, name, pic_uri, birthday, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                _userId = getIntent().getStringExtra("_userId");
                name = getIntent().getStringExtra("name");
                email = getIntent().getStringExtra("email");
                pic_uri = getIntent().getStringExtra("profile_pic");
                birthday = getIntent().getStringExtra("birthday");
                Log.i("name??", "print" + name);
                Log.i("birthday", "primt" + birthday);
                Intent intent1 = new Intent(getApplicationContext(), MyClubActivity.class); // 새로운 창을 만들기위한 객체
                intent1.putExtra("_userId", _userId);

                intent1.putExtra("name", name);
                intent1.putExtra("email", email);
                intent1.putExtra("pic_uri", pic_uri);
                intent1.putExtra("birthday", birthday);
                startActivity(intent1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                name = getIntent().getStringExtra("name");
                Intent intent2 = new Intent(getApplicationContext(), MyPostActivity.class); // 새로운 창을 만들기위한 객체
                intent2.putExtra("name", name);
                startActivity(intent2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                name = getIntent().getStringExtra("name");
                Intent intent3 = new Intent(getApplicationContext(), MyCommentActivity.class); // 새로운 창을 만들기위한 객체
                intent3.putExtra("name", name);
                startActivity(intent3);
            }
        });

    }
}

