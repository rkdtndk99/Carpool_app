package com.example.everyClub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ProfileFragment extends Fragment {
    String name,pic_uri,birthday, email;
    View view;
    TextView p_name, p_email, p_birthday;
    ImageView profile_img;

    public ProfileFragment(String name, String pic_uri, String birthday, String email){
        this.name = name;
        this.pic_uri = pic_uri;
        this.birthday = birthday;
        this.email = email;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile, container, false);

        p_name = view.findViewById(R.id.p_name);
        p_email = view.findViewById(R.id.p_email);
        p_birthday = view.findViewById(R.id.p_birthday);
        profile_img = view.findViewById(R.id.profile_pic);

        Log.i("name!!!!", "print" + name);
        Log.i("email!!!!", "print" + email);
        Log.i("birthday!!!!", "print" + birthday);

        p_name.setText(name);
        p_email.setText(email);
        p_birthday.setText(birthday);

        if(pic_uri == null){
            @SuppressLint("UseCompatLoadingForDrawables")
            BitmapDrawable img = (BitmapDrawable)getResources().getDrawable(R.drawable.user);
            profile_img.setImageDrawable(img);
        }else {
            Glide.with(getActivity()).load(pic_uri).into(profile_img);
        }
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LandingActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}