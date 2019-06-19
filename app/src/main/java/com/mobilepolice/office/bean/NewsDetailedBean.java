package com.mobilepolice.office.bean;

import java.util.List;

public class NewsDetailedBean extends BaseBean {

    /**
     * id : 1
     * meetName : 省政府召开党组（扩大）会议
     * shortCon : 省残疾人中等开干部作风大整顿活动专题会议
     * hostName : 景俊海
     * time : 2018-12-17
     * resource : 吉林省政府网
     * newsCon : ["<img src=\"img/news-details-img@2x.png\" alt=\"\">","根据吉林省残联干部作风大整顿活动领导小组办公室《关于机关部室、直属单位主要领导开展谈心谈话和直属单位班子干部作风大整顿活动专题会议安排》的通知要求，提请省残联干部作风大整顿活动领导小组办公室同意，省残疾人中等职业学校于10月11日召开了干部作风大整顿活动专题会议，省残联党组成员、副理事长万宇出席了本次会议。","会上，省残疾人中等职业学校书记从破\u201c五弊\u201d5个清单，查摆梳理突出问题、交出\u201c5笔账\u201d及下一步工作打算三个方面着手，对省残疾人中等职业学校参与为期50天的全省干部作风大整顿活动进行了总结汇报。"]
     */

    private String id;
    private String meetName;
    private String shortCon;
    private String hostName;
    private String time;
    private String resource;
    private List<String> newsCon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeetName() {
        return meetName;
    }

    public void setMeetName(String meetName) {
        this.meetName = meetName;
    }

    public String getShortCon() {
        return shortCon;
    }

    public void setShortCon(String shortCon) {
        this.shortCon = shortCon;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public List<String> getNewsCon() {
        return newsCon;
    }

    public void setNewsCon(List<String> newsCon) {
        this.newsCon = newsCon;
    }
}
