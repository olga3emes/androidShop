package com.example.olga.shop.rss;

/**
 * Created by olga on 30/5/17.
 */

public class Report {


    private String title;
    private String link;
    private String description;
    private String date;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String t) {
        title = t;
    }

    public void setLink(String l) {
        link = l;
    }

    public void setDescription(String d) {
        description = d;
    }

    public void setDate(String f) {
        date = f;
    }


    @Override
    public String toString() {
        return title.toUpperCase()
                + System.getProperty("line.separator") +
                " Date: " + date.replace("+0000","") + System.getProperty("line.separator") +
                description.replaceAll("\\<.*?\\>", " ")+ System.getProperty("line.separator")
                +link + System.getProperty("line.separator");
    }

}
