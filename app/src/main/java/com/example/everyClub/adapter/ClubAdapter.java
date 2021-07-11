package com.example.everyClub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.everyClub.R;
import com.example.everyClub.data.Club;

import java.util.ArrayList;
import java.util.List;

public class ClubAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    List<Club> clubList;

    public ClubAdapter(Context context, List<Club> data) {
        mContext = context;
        clubList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    public void updateReceiptsList(ArrayList<Club> data) {
        clubList = data;
        this.notifyDataSetChanged(); // 그냥 여기서 하자
    }

    @Override
    public int getCount() {
        return clubList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Club getItem(int position) {
        return clubList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.club_list_item, null);
        TextView ClubName = (TextView)view.findViewById(R.id.club_name);
        TextView ClubOwner = (TextView)view.findViewById(R.id.club_owner);
        TextView ClubDesc = (TextView)view.findViewById(R.id.club_desc);

        ClubName.setText(clubList.get(position).getClubName());
        ClubOwner.setText(clubList.get(position).getClubOwner());
        ClubDesc.setText(clubList.get(position).getClubDesc());

        return view;
    }
}
