package com.example.newsapp;

public class News {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public News(String title, String description, String source, String date) {
        this.title = title;
        this.description = description;
        this.source = source;
        this.date = date;
    }
    String title;
    String description;
    String source;
    String date;
}
