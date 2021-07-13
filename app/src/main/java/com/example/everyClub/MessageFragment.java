package com.example.everyClub;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MessageFragment extends Fragment {
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_msg, container, false);
        EditText editText = view.findViewById(R.id.editText);

        view.findViewById(R.id.enterBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("name", editText.getText().toString());

                ((MyClubActivity)getActivity()).cf.setArguments(bundle);
                ((MyClubActivity)getActivity()).setFrag(5);
            }
        });

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