package com.example.everyClub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.everyClub.R;
import com.example.everyClub.Table;

import java.util.List;

public class TableAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    List<Table> tableList;

    public TableAdapter(Context context, List<Table> data) {
        mContext = context;
        tableList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return tableList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Table getItem(int position) {
        return tableList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.table_list_item, null);
        TextView tableTitle = (TextView)view.findViewById(R.id.table_title);
        TextView tableContent = (TextView)view.findViewById(R.id.table_content);

        tableTitle.setText(tableList.get(position).getTitle());
        tableContent.setText(tableList.get(position).getContent());

        return view;
    }
}
