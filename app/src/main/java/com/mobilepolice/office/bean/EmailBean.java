package com.mobilepolice.office.bean;

public class EmailBean {

    private String date;
    private String title;
    private String name;
    private String content;

    public EmailBean() {
    }

    public EmailBean(String name, String title, String date,String content) {
        this.date = date;
        this.title = title;
        this.name = name;
        this.content=content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
