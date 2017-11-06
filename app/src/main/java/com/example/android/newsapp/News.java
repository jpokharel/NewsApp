package com.example.android.newsapp;

/**
 * Created by jiwanpokharel89 on 10/29/2017.
 */

public class News {
    private String id;
    private String title;
    private String section;
    private String description;
    private String author;
    private String datePublished;

    public News(String id, String title, String section, String description, String author, String datePublished) {
        this.id = id;
        this.title = title;
        this.section = section;
        this.description = description;
        this.author = author;
        this.datePublished = datePublished;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }
}
