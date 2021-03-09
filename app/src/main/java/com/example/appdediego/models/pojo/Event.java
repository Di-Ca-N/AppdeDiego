package com.example.appdediego.models.pojo;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    private Integer id;
    private String title;
    private Date date;
    private String description;
    private String type;

    public Event(int id, String title, Date date, String description, String type) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.type = type;
    }

    public Event(String title, Date date, String description, String type) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.type = type;
    }

    @NotNull
    @Override
    public String toString() {
        return this.type + ": " + this.title;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
