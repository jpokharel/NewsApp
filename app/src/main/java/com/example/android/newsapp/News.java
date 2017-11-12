package com.example.android.newsapp;

import static android.R.attr.description;

/**
 * Created by jiwanpokharel89 on 10/29/2017.
 */

public class News {
    private String id;
    private String title;
    private String section;
    private String author;
    private String datePublished;
    private String url;

    public News(String id, String title, String section, String author, String datePublished, String url) {
        this.id = id;
        this.title = title;
        this.section = section;
        this.author = author;
        this.datePublished = datePublished;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", section='" + section + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", datePublished='" + datePublished + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
