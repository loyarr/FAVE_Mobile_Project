package com.example.aksub_projectmobile.models;

public class FavoriteQuotes {

    private String author, quote;
    private int id;

    public FavoriteQuotes(int id, String author, String quote) {
        this.author = author;
        this.quote = quote;
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
