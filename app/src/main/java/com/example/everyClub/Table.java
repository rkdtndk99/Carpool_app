package com.example.everyClub;

public class Table {

    private String name;
    private String title;
    private String content;
    private String clubName;

    public Table(String name, String title, String content, String clubName) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.clubName = clubName;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {return content;}

    public String getClubName() {return clubName;}
}
