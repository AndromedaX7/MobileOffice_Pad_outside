package com.mobilepolice.office.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.mobilepolice.office.utils.DateUtil;

import java.util.Collections;
import java.util.List;

public class CollectEmailBean {

    /**
     * msg : 成功！
     * obj : [{"isAttach":true,"sender":"姜蕾<jianglei@gat.jl>","sendDate":"2019-02-12 15:26:18","attach":[{"fileName":"GA一网双域安全升级改造方案（含报价）.docx","filePath":"http://10.106.12.104:8789/mailfile/1551162729259.docx"},{"fileName":"GA一网双域安全升级改造方案（含报价）.docx","filePath":"http://10.106.12.104:8789/mailfile/1551162729259.docx"}],"id":"20190212071827.1D509340074@mail1.gat.jl","title":"数据资源整合（中管）.docx","isSeen":true,"content":"<br><DIV><\/DIV>"},{"isAttach":true,"sender":"dmxxfw@gat.jl<dmxxfw@gat.jl>","sendDate":"2019-02-13 14:46:47","attach":[{"fileName":"GA一网双域安全升级改造方案（含报价）.docx","filePath":"http://10.106.12.104:8789/mailfile/1551162729259.docx"},{"fileName":"GA一网双域安全升级改造方案（含报价）.docx","filePath":"http://10.106.12.104:8789/mailfile/1551162729259.docx"}],"id":"201902131447458742301@gat.jl","title":"转发: 重点人员类别字典","isSeen":true,"content":"<br><html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=GB2312\"><style>body { line-height: 1.5; }blockquote { margin-top: 0px; margin-bottom: 0px; margin-left: 0.5em; }div.foxdiv20190213144741745660 { }body { font-size: 10.5pt; font-family: 微软雅黑; color: rgb(0, 0, 0); line-height: 1.5; }<\/style><\/head><body>\n<div><span><\/span><br><\/div>\n<div><br><\/div><hr style=\"width: 210px; height: 1px;\" color=\"#b5c4df\" size=\"1\" align=\"left\">\n<div><span><div style=\"MARGIN: 10px; FONT-FAMILY: verdana; FONT-SIZE: 10pt\"><div>dmxxfw@gat.jl<\/div><\/div><\/span><\/div>\n<blockquote style=\"margin-top: 0px; margin-bottom: 0px; margin-left: 0.5em;\"><div>&nbsp;<\/div><div style=\"border:none;border-top:solid #B5C4DF 1.0pt;padding:3.0pt 0cm 0cm 0cm\"><div style=\"PADDING-RIGHT: 8px; PADDING-LEFT: 8px; FONT-SIZE: 12px;FONT-FAMILY:tahoma;COLOR:#000000; BACKGROUND: #efefef; PADDING-BOTTOM: 8px; PADDING-TOP: 8px\"><div><b>发件人：<\/b>&nbsp;<a href=\"mailto:qbao@gat.jl\">qbao@gat.jl<\/a><\/div><div><b>发送时间：<\/b>&nbsp;2017-10-07&nbsp;15:11<\/div><div><b>收件人：<\/b>&nbsp;<a href=\"mailto:dmxxfw@gat.jl\">dmxxfw<\/a><\/div><div><b>主题：<\/b>&nbsp;重点人员类别字典<\/div><\/div><\/div><div><div class=\"FoxDiv20190213144741745660\">\n<div><span><\/span><br><\/div>\n<div><br><\/div><hr style=\"width: 210px; height: 1px;\" color=\"#b5c4df\" size=\"1\" align=\"left\">\n<div><span><div style=\"MARGIN: 10px; FONT-FAMILY: verdana; FONT-SIZE: 10pt\"><div>qbao@gat.jl<\/div><\/div><\/span><\/div>\n<\/div><\/div><\/blockquote>\n<\/body><\/html>"},{"isAttach":true,"sender":"liuyuheng@gat.jl<liuyuheng@gat.jl>","sendDate":"2019-02-14 16:14:19","attach":[{"fileName":"GA一网双域安全升级改造方案（含报价）.docx","filePath":"http://10.106.12.104:8789/mailfile/1551162729259.docx"},{"fileName":"GA一网双域安全升级改造方案（含报价）.docx","filePath":"http://10.106.12.104:8789/mailfile/1551162729259.docx"}],"id":"201902141619143001761@gat.jl","title":"回复: （新表格）各省新一代移动警务建设进度","isSeen":true,"content":"<br><html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=GB2312\"><style>body { line-height: 1.5; }blockquote { margin-top: 0px; margin-bottom: 0px; margin-left: 0.5em; }div.foxdiv20190214161911968777 { }body { font-size: 10.5pt; font-family: 微软雅黑; color: rgb(0, 0, 0); line-height: 1.5; }<\/style><\/head><body>\n<div><span><\/span><br><\/div>\n<div><br><\/div><hr style=\"width: 210px; height: 1px;\" color=\"#b5c4df\" size=\"1\" align=\"left\">\n<div><span><div style=\"MARGIN: 10px; FONT-FAMILY: verdana; FONT-SIZE: 10pt\"><div>姓名：刘宇恒<\/div><div>联系电话：82098639<\/div><div>手机：18043006783<\/div><\/div><\/span><\/div>\n<blockquote style=\"margin-top: 0px; margin-bottom: 0px; margin-left: 0.5em;\"><div>&nbsp;<\/div><div style=\"border:none;border-top:solid #B5C4DF 1.0pt;padding:3.0pt 0cm 0cm 0cm\"><div style=\"PADDING-RIGHT: 8px; PADDING-LEFT: 8px; FONT-SIZE: 12px;FONT-FAMILY:tahoma;COLOR:#000000; BACKGROUND: #efefef; PADDING-BOTTOM: 8px; PADDING-TOP: 8px\"><div><b>发件人：<\/b>&nbsp;<a href=\"mailto:cuinan@gat.jl\">cuinan@gat.jl<\/a><\/div><div><b>发送时间：<\/b>&nbsp;2019-02-14&nbsp;10:32<\/div><div><b>收件人：<\/b>&nbsp;<a href=\"mailto:liuyuheng@gat.jl\">liuyuheng<\/a><\/div><div><b>主题：<\/b>&nbsp;（新表格）各省新一代移动警务建设进度<\/div><\/div><\/div><div><div class=\"FoxDiv20190214161911968777\">\n<div><span><\/span><br><\/div>\n<div><br><\/div><hr style=\"width: 210px; height: 1px;\" color=\"#b5c4df\" size=\"1\" align=\"left\">\n<div><span><div style=\"MARGIN: 10px; FONT-FAMILY: verdana; FONT-SIZE: 10pt\"><div>cuinan@gat.jl<\/div><\/div><\/span><\/div>\n<\/div><\/div><\/blockquote>\n<\/body><\/html>"},{"isAttach":true,"sender":"dmxxfw@gat.jl<dmxxfw@gat.jl>","sendDate":"2019-02-19 16:09:36","attach":[{"fileName":"GA一网双域安全升级改造方案（含报价）.docx","filePath":"http://10.106.12.104:8789/mailfile/1551162729259.docx"},{"fileName":"GA一网双域安全升级改造方案（含报价）.docx","filePath":"http://10.106.12.104:8789/mailfile/1551162729259.docx"}],"id":"201902191636088216810@gat.jl","title":"旅馆业数据汇集采集信息-上报物理表-吉林(调整)","isSeen":true,"content":"<br><html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=GB2312\"><style>body { line-height: 1.5; }body { font-size: 10.5pt; font-family: 微软雅黑; color: rgb(0, 0, 0); line-height: 1.5; }<\/style><\/head><body>\n<div><span><\/span><br><\/div>\n<div><br><\/div><hr style=\"width: 210px; height: 1px;\" color=\"#b5c4df\" size=\"1\" align=\"left\">\n<div><span><div style=\"MARGIN: 10px; FONT-FAMILY: verdana; FONT-SIZE: 10pt\"><div>dmxxfw@gat.jl<\/div><\/div><\/span><\/div>\n<\/body><\/html>"},{"isAttach":true,"sender":"liuyuheng@gat.jl<liuyuheng@gat.jl>","sendDate":"2019-02-20 08:12:46","attach":[{"fileName":"GA一网双域安全升级改造方案（含报价）.docx","filePath":"http://10.106.12.104:8789/mailfile/1551162729259.docx"},{"fileName":"GA一网双域安全升级改造方案（含报价）.docx","filePath":"http://10.106.12.104:8789/mailfile/1551162729259.docx"}],"id":"201902200846119729190@gat.jl","title":"开发文档接口规范2018.9.5","isSeen":true,"content":"<br><html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=GB2312\"><style>body { line-height: 1.5; }body { font-size: 10.5pt; font-family: 微软雅黑; color: rgb(0, 0, 0); line-height: 1.5; }<\/style><\/head><body>\n<div><span><\/span><br><\/div>\n<div><br><\/div><hr style=\"width: 210px; height: 1px;\" color=\"#b5c4df\" size=\"1\" align=\"left\">\n<div><span><div style=\"MARGIN: 10px; FONT-FAMILY: verdana; FONT-SIZE: 10pt\"><div>姓名：刘宇恒<\/div><div>联系电话：82098639<\/div><div>手机：18043006783<\/div><\/div><\/span><\/div>\n<\/body><\/html>"},{"isAttach":true,"sender":"赵亮<zhaol@gat.jl>","sendDate":"2019-02-20 13:41:32","attach":[{"fileName":"GA一网双域安全升级改造方案（含报价）.docx","filePath":"http://10.106.12.104:8789/mailfile/1551162729259.docx"},{"fileName":"GA一网双域安全升级改造方案（含报价）.docx","filePath":"http://10.106.12.104:8789/mailfile/1551162729259.docx"}],"id":"201902201332414214204@gat.jl","title":"GA一网双域安全升级改造方案（含报价）","isSeen":true,"content":"<br><html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=GB2312\"><style>body { line-height: 1.5; }body { font-size: 10.5pt; font-family: Tahoma; color: rgb(0, 0, 0); line-height: 1.5; }<\/style><\/head><body> \n<div style=\"FONT-FAMILY: Verdana\">\n<div>你好！ <\/div>\n<div>&nbsp;<\/div>\n<div><span><\/span><\/div>\n<div>&nbsp;<\/div><div><br><\/div>\n<div>\\||/-----------------------------\\||/&nbsp;<br>|工|发件人:赵亮 &nbsp;　&nbsp;　　　　 &nbsp; &nbsp; &nbsp; &nbsp;|四|<br>|作|邮件:zhaol@gat.jl&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; |季|<br>|顺|部门:吉林省公安厅科技通信处　|<font size=\"2\"><span style=\"line-height: 19px;\">平<\/span><\/font><span style=\"font-size: 10.5pt; line-height: 1.5; background-color: window;\">|<\/span><\/div><div>|利|地址:长春市朝阳区新发路806号<span style=\"font-size: 10.5pt; line-height: 1.5; background-color: window;\">|安<\/span>|<span style=\"font-size: 10pt; line-height: 1.5; background-color: window;\">&nbsp;<\/span><\/div><div>|身|邮编:130051　 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;|八|<br>|体|手机:18043002803　 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; |方|<br>|健|座机:0431\u2014\u201482098861　　 |吉|<br>|康|日期:2019-02-20|10:27:13&nbsp;&nbsp;|祥|<br>/||\\-----------------------------<span style=\"background-color: window; font-size: 10pt; line-height: 1.5;\">/||\\<\/span><\/div><\/div>\n<\/body><\/html>"}]
     * success : true
     * attributes : null
     */

    private String msg;
    private boolean success;
    private Object attributes;
    private List<ObjBean> obj;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public List<ObjBean> getObj() {
        Collections.sort(obj);
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean implements Comparable<ObjBean>,Parcelable {
        /**
         * isAttach : true
         * sender : 姜蕾<jianglei@gat.jl>
         * sendDate : 2019-02-12 15:26:18
         * attach : [{"fileName":"GA一网双域安全升级改造方案（含报价）.docx","filePath":"http://10.106.12.104:8789/mailfile/1551162729259.docx"},{"fileName":"GA一网双域安全升级改造方案（含报价）.docx","filePath":"http://10.106.12.104:8789/mailfile/1551162729259.docx"}]
         * id : 20190212071827.1D509340074@mail1.gat.jl
         * title : 数据资源整合（中管）.docx
         * isSeen : true
         * content : <br><DIV></DIV>
         */

        private boolean isAttach;
        private String sender;
        private String sendDate;
        private String id;
        private String title;
        private boolean isSeen;
        private String content;
        private List<AttachBean> attach;

        public boolean isIsAttach() {
            return isAttach;
        }

        public void setIsAttach(boolean isAttach) {
            this.isAttach = isAttach;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getSendDate() {
            return sendDate;
        }

        public void setSendDate(String sendDate) {
            this.sendDate = sendDate;
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

        public boolean isIsSeen() {
            return isSeen;
        }

        public void setIsSeen(boolean isSeen) {
            this.isSeen = isSeen;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<AttachBean> getAttach() {
            return attach;
        }

        public void setAttach(List<AttachBean> attach) {
            this.attach = attach;
        }

        public static class AttachBean implements Parcelable {
            /**
             * fileName : GA一网双域安全升级改造方案（含报价）.docx
             * filePath : http://10.106.12.104:8789/mailfile/1551162729259.docx
             */

            private String fileName;
            private String filePath;

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public String getFilePath() {
                return filePath;
            }

            public void setFilePath(String filePath) {
                this.filePath = filePath;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.fileName);
                dest.writeString(this.filePath);
            }

            public AttachBean() {
            }

            protected AttachBean(Parcel in) {
                this.fileName = in.readString();
                this.filePath = in.readString();
            }

            public static final Creator<AttachBean> CREATOR = new Creator<AttachBean>() {
                @Override
                public AttachBean createFromParcel(Parcel source) {
                    return new AttachBean(source);
                }

                @Override
                public AttachBean[] newArray(int size) {
                    return new AttachBean[size];
                }
            };
        }

        @Override
        public int compareTo(@NonNull ObjBean o) {
            if ( DateUtil.parseDate("yyyy-MM-dd HH:mm:ss",sendDate)> DateUtil.parseDate("yyyy-MM-dd HH:mm:ss",o.sendDate))
                return -1;
            else if ( DateUtil.parseDate("yyyy-MM-dd HH:mm:ss",sendDate)<DateUtil.parseDate("yyyy-MM-dd HH:mm:ss",o.sendDate)){
                return 1;
            }
            return 0;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.isAttach ? (byte) 1 : (byte) 0);
            dest.writeString(this.sender);
            dest.writeString(this.sendDate);
            dest.writeString(this.id);
            dest.writeString(this.title);
            dest.writeByte(this.isSeen ? (byte) 1 : (byte) 0);
            dest.writeString(this.content);
            dest.writeTypedList(this.attach);
        }

        public ObjBean() {
        }

        protected ObjBean(Parcel in) {
            this.isAttach = in.readByte() != 0;
            this.sender = in.readString();
            this.sendDate = in.readString();
            this.id = in.readString();
            this.title = in.readString();
            this.isSeen = in.readByte() != 0;
            this.content = in.readString();
            this.attach = in.createTypedArrayList(AttachBean.CREATOR);
        }

        public static final Creator<ObjBean> CREATOR = new Creator<ObjBean>() {
            @Override
            public ObjBean createFromParcel(Parcel source) {
                return new ObjBean(source);
            }

            @Override
            public ObjBean[] newArray(int size) {
                return new ObjBean[size];
            }
        };
    }
}
