package com.example.carpool_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.sql.DriverManager.println;

public class LoginActivity extends AppCompatActivity {
    EditText edtext_id, edtext_pw;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtext_id = findViewById(R.id.id);
        edtext_pw = findViewById(R.id.pw);
        login_btn = findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strId = edtext_id.getText().toString();
                String strPw = edtext_pw.getText().toString();
                request();
            }
        });
    }

    public void request(){
        String url = "http://172.10.18.120";
        JSONObject testjson = new JSONObject();
        try {
            String strId = edtext_id.getText().toString();
            String strPw = edtext_pw.getText().toString();

            testjson.put("id", strId);
            testjson.put("password", strPw);
            String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.i("response", "데이터 전송 성공");

                        //받은 json형식의 응답을 받아
                        JSONObject jsonObject = new JSONObject(response.toString());

                        //key값에 따라 value값을 쪼개 받아옵니다.
                        String resultId = jsonObject.getString("approve_id");
                        String resultPassword = jsonObject.getString("approve_pw");

                        //만약 그 값이 같다면 로그인에 성공한 것입니다.
                        if(resultId.equals("OK") & resultPassword.equals("OK")){  //로그인 성공
                            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                            startActivity(intent);
                        }else{
                            //로그인에 실패했을 경우 실행할 코드를 입력하시면 됩니다.
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Log.i("failed", "failed connecting server");
                    //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);
            //
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}