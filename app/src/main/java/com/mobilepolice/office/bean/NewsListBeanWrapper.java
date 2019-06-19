package com.mobilepolice.office.bean;

public class NewsListBeanWrapper {
    private int type ;
    private  NewsListBean data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public NewsListBean getData() {
        return data;
    }

    public void setData(NewsListBean data) {
        this.data = data;
    }

    public NewsListBeanWrapper() {

    }

    public NewsListBeanWrapper(int type, NewsListBean data) {

        this.type = type;
        this.data = data;
    }
}
