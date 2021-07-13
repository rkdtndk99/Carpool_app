package com.example.everyClub.data;

import android.os.Parcel;

public class Table {
    private String _id;
    private String name;
    private String title;
    private String content;
    private String updated;

    public Table(String name, String title, String content) {
        this._id = _id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.updated = updated;
    }
    public String get_id() {return _id;}

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUpdated() {
        return updated;
    }
}
