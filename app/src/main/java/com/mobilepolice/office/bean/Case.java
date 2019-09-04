package com.mobilepolice.office.bean;


/**
 * 案件
 */
public class Case {

    private String name;//标题
    private String polices;//民警
    private String type;//类型
    private String time;//时间
    private String state;//状态

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPolices() {
        return polices;
    }

    public void setPolices(String polices) {
        this.polices = polices;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
