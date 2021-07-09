package com.example.everyClub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyClubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myclub_page);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);

        TextView nameText = (TextView)findViewById(R.id.nameText);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        nameText.setText(name);

        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent1 = new Intent(getApplicationContext(), TableListActivity.class); // 새로운 창을 만들기위한 객체
                intent1.putExtra("name", name);
                startActivity(intent1);
            }
        });
    }
}


