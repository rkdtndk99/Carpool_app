package com.example.everyClub.data;

public class Table {

    private String _id;
    private String name;
    private String title;
    private String content;
    private String clubName;
    private String updated;

    public Table(String _id, String name, String title, String content, String clubName, String updated) {
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
}
