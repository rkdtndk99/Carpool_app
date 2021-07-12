package com.example.everyClub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LandingActivity extends AppCompatActivity {
    String name,pic_uri,birthday, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        TextView nameText = (TextView)findViewById(R.id.nameText);
        Intent intent = getIntent();


        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent1 = new Intent(getApplicationContext(), MyClubActivity.class); // 새로운 창을 만들기위한 객체
                name = intent.getStringExtra("name");
                email = intent.getStringExtra("email");
                pic_uri = intent.getStringExtra("profile_pic");
                birthday = intent.getStringExtra("birthday");
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

