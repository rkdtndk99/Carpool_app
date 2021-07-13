package com.example.everyClub;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.everyClub.adapter.MessageAdapter;
import com.kakao.auth.authorization.AuthorizationResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import static android.app.Activity.RESULT_OK;

public class ChatFragment extends Fragment implements TextWatcher {
    View view;
    private String name;
    private WebSocket webSocket;
    private String SERVER_PATH = "http://192.249.18.120:80";
    private EditText messageEdit;
    private View sendBtn, pickImgBtn;
    private RecyclerView recyclerView;
    private final int IMAGE_REQUEST_ID = 1;
    private MessageAdapter messageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_chat, container, false);

        Bundle bundle = getArguments();
        name = bundle.getString("name");

        initiateSocketConnection();

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MyClubActivity)getActivity()).setFrag(3);
            }
        });
        return view;
    }

    private void initiateSocketConnection() {
        Log.i("Socket", "connected");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = client.newWebSocket(request, new SocketListener());

    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }


    public void afterTextChanged(Editable s) {

        String string = s.toString().trim();

        if (string.isEmpty()) {
            resetMessageEdit();
        } else {

            sendBtn.setVisibility(View.VISIBLE);
            pickImgBtn.setVisibility(View.INVISIBLE);
        }

    }

    private void resetMessageEdit() {

        messageEdit.removeTextChangedListener((TextWatcher) this);

        messageEdit.setText("");
        sendBtn.setVisibility(View.INVISIBLE);
        pickImgBtn.setVisibility(View.VISIBLE);

        messageEdit.addTextChangedListener((TextWatcher) this);

    }

    private class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);

            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(),
                        "Socket Connection Successful!",
                        Toast.LENGTH_SHORT).show();

                initializeView();
            });
        }

        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);

            getActivity().runOnUiThread(() -> {

                try {
                    JSONObject jsonObject = new JSONObject(text);
                    jsonObject.put("isSent", false);

                    messageAdapter.addItem(jsonObject);

                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });

        }
    }

    private void initializeView() {

        messageEdit = view.findViewById(R.id.messageEdit);
        sendBtn = view.findViewById(R.id.sendBtn);
        pickImgBtn = view.findViewById(R.id.pickImgBtn);

        recyclerView = view.findViewById(R.id.recyclerView);

        messageAdapter = new MessageAdapter(getLayoutInflater());
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messageEdit.addTextChangedListener((TextWatcher) this);
        sendBtn.setOnClickListener(v -> {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", name);
                jsonObject.put("message", messageEdit.getText().toString());

                webSocket.send(jsonObject.toString());

                jsonObject.put("isSent", true);
                messageAdapter.addItem(jsonObject);

                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                resetMessageEdit();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

        pickImgBtn.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            Log.i("Image1","Success");
            startActivityForResult(Intent.createChooser(intent, "Pick image"),
                    IMAGE_REQUEST_ID);

        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Image2","Success");

        if (requestCode == IMAGE_REQUEST_ID && resultCode == RESULT_OK) {

            try {
                Log.i("Image3","Success");
                InputStream is = getActivity().getContentResolver().openInputStream(data.getData());
                Bitmap image = BitmapFactory.decodeStream(is);

                sendImage(image);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    private void sendImage(Bitmap image) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);

        String base64String = Base64.encodeToString(outputStream.toByteArray(),
                Base64.DEFAULT);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", name);
            jsonObject.put("image", base64String);

            webSocket.send(jsonObject.toString());

            jsonObject.put("isSent", true);

            messageAdapter.addItem(jsonObject);

            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
