package com.example.everyClub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.everyClub.R;
import com.example.everyClub.data.Comment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

//오빠 CommentAdapter랑 동일

public class CommentViewAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    List<Comment> commentList = null;

    public CommentViewAdapter(Context context, List<Comment> data) {
        this.mContext = context;
        this.commentList = data;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        //return commentList.size();
        if (commentList!=null){
            return commentList.size();
        }
       return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Comment getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.comment_list_item, null);
        TextView commentName = (TextView) view.findViewById(R.id.comment_name);
        TextView commentContent = (TextView) view.findViewById(R.id.comment_content);

        commentName.setText(commentList.get(position).getName());
        commentContent.setText(commentList.get(position).getContent());

        return view;
    }
}
