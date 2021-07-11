package com.example.everyClub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.everyClub.R;
import com.example.everyClub.data.Table;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    List<Table> tableList;

    public ListViewAdapter(Context context, List<Table> data) {
        mContext = context;
        tableList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return tableList.size();
    }

    @Override
    public Object getItem(int position) {
        return tableList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_item, null);
        TextView tableTitle = (TextView)view.findViewById(R.id.table_title);
        TextView tableContent = (TextView)view.findViewById(R.id.table_content);

        tableTitle.setText(tableList.get(position).getTitle());
        tableContent.setText(tableList.get(position).getContent());

        return view;
    }
}
