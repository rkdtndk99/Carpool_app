package com.example.everyClub.data;

public class Club {

    private String _id;
    private String clubOwner;
    private String clubName;
    private String clubDesc;

    public Club(String _id, String clubOwner, String clubName, String clubDesc) {
        this._id = _id;
        this.clubOwner = clubOwner;
        this.clubName = clubName;
        this.clubDesc = clubDesc;
    }

    public String get_id() {return _id;}

    public String getClubOwner() {
        return clubOwner;
    }

    public String getClubName() {
        return clubName;
    }

    public String getClubDesc() {
        return clubDesc;
    }
}
