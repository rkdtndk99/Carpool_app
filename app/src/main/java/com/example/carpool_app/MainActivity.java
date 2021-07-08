package com.example.carpool_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button button1;
    TextView textView1;
    public static final String TAG = "MyRequestQueue";
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                //requestProcess();
            }
        });

        // RequestQueue 객체생성
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    public void requestProcess() {
        String url = "http://172.10.18.147/users";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    // 요청을 보내고 응답받았을때
                    @Override
                    public void onResponse(String response) {
                        println("응답 -> " + response);
                    }
                },
                new Response.ErrorListener() {
                    // 요청보내고 에러 발생시에 호출되는 리스너
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러 -> " + error.getMessage());
                    }
                }
        ) {

            // POST 방식으로 요청할 경우에 전달하는 파라미터값들 지정
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //return super.getParams();
                Map<String,String> params = new HashMap<String,String>();
                return params;
            }
        };
        request.setTag(TAG);

        // 캐쉬기능을 끊다. 바로바로 내용처리되도록
        request.setShouldCache(false);
        requestQueue.add(request);

    }

    public void println(String data) {
        textView1.setText(data + "\n");
    }

    protected void onStop() {
        super.onStop();

        if (requestQueue != null) {
            // RequestQueue 의 TAG 값으로 지정된 Queue 안의 모든 request들을 취소한다.
            requestQueue.cancelAll(TAG);
        }
    }
}
