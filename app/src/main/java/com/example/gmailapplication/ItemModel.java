package com.example.gmailapplication;

import java.util.Random;

public class ItemModel {
    String name;
    String subject;
    String content;
    String time;
    boolean isFavorite;
    int color;

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public int getColor() {
        return color;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public ItemModel (String name, String subject, String content, String time) {
        this.name = name;
        this.subject = subject;
        this.content = content;
        this.time = time;
        isFavorite = false;
        Random random = new Random();
        color = random.nextInt();
    }
}
