package com.example.everyClub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.everyClub.login.MainActivity;

public class LandingActivity extends AppCompatActivity {
    private String name, email, pic_uri, birthday;

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
                Intent intent = getIntent();
                name = intent.getStringExtra("name");
                email = intent.getStringExtra("email");
                pic_uri = intent.getStringExtra("profile_pic");
                birthday = intent.getStringExtra("birthday");

                Intent intent1 = new Intent(LandingActivity.this, MyClubActivity.class); // 새로운 창을 만들기위한 객체
                intent1.putExtra("name", name);
                intent1.putExtra("email", email);
                intent1.putExtra("profile_pic",pic_uri);
                intent1.putExtra("birthday", birthday);
                startActivity(intent1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent2 = new Intent(getApplicationContext(), FindClubActivity.class); // 새로운 창을 만들기위한 객체
                startActivity(intent2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent3 = new Intent(getApplicationContext(), RegisterClubActivity.class); // 새로운 창을 만들기위한 객체
                startActivity(intent3);
            }
        });

    }
}

