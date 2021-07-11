package com.example.everyClub.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Table implements Parcelable {
    private String _id;
    private String name;
    private String title;
    private String content;
    private String clubName;
    private String updated;

    public Table(String name, String title, String content, String clubName) {
        this._id = _id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.clubName = clubName;
        this.updated = updated;
    }
    public String get_id() {return _id;}

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {return content;}

    public String getClubName() {return clubName;}

    public String getUpdated() {return updated;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
