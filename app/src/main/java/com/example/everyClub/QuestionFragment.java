package com.example.everyClub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class QuestionFragment extends Fragment {
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_question, container, false);
        view.findViewById(R.id.btn_new_q).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyClubActivity) getActivity()).setFrag(6);
            }
        });
        return view;
    }
}