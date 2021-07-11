package com.example.everyClub.data;

public class Comment {
    private String _id;
    private String tableId;
    private String name;
    private String content;
    private String updated;

    public Comment(String _id, String tableId, String name, String content, String updated) {
        this._id = _id;
        this.tableId = tableId;
        this.name = name;
        this.content = content;
        this.updated = updated;
    }

    public String get_id() {return _id;}

    public String getTableId() {return tableId;}

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getUpdated() {return updated;}
}
