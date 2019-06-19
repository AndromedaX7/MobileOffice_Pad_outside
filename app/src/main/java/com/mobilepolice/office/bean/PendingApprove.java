package com.mobilepolice.office.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PendingApprove {
    /**
     * msg : 操作成功
     * obj : [{"overFlag":"0","applyPersonName":"段科员","approveNode":"","title":"标题1","applyOffWordFile":"http://www.freetk.cn:8789/mobileworkws-image/document/3E625BE83C7A419A8640BA5E81D5CC77-110755.jpg","applyPerson":"110755","approvePersonName":"","urgentLevel":"1","requestid":"3E625BE83C7A419A8640BA5E81D5CC77","approvePerson":"100935","approveNodeId":"1","id":"F46438BC73054267B807B85A1496B5BC","createDate":"2019-03-08 10:18:19"}]
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
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean implements Parcelable {
        /**
         * overFlag : 0
         * applyPersonName : 段科员
         * approveNode :
         * title : 标题1
         * applyOffWordFile : http://www.freetk.cn:8789/mobileworkws-image/document/3E625BE83C7A419A8640BA5E81D5CC77-110755.jpg
         * applyPerson : 110755
         * approvePersonName :
         * urgentLevel : 1
         * requestid : 3E625BE83C7A419A8640BA5E81D5CC77
         * approvePerson : 100935
         * approveNodeId : 1
         * id : F46438BC73054267B807B85A1496B5BC
         * createDate : 2019-03-08 10:18:19
         */

        private String overFlag;
        private String applyPersonName;
        private String approveNode;
        private String title;
        private String applyOffWordFile;
        private String applyPerson;
        private String approvePersonName;
        private String urgentLevel;
        private String requestid;
        private String approvePerson;
        private String approveNodeId;
        private String id;
        private String createDate;

        public String getOverFlag() {
            return overFlag;
        }

        public void setOverFlag(String overFlag) {
            this.overFlag = overFlag;
        }

        public String getApplyPersonName() {
            return applyPersonName;
        }

        public void setApplyPersonName(String applyPersonName) {
            this.applyPersonName = applyPersonName;
        }

        public String getApproveNode() {
            return approveNode;
        }

        public void setApproveNode(String approveNode) {
            this.approveNode = approveNode;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getApplyOffWordFile() {
            return applyOffWordFile;
        }

        public void setApplyOffWordFile(String applyOffWordFile) {
            this.applyOffWordFile = applyOffWordFile;
        }

        public String getApplyPerson() {
            return applyPerson;
        }

        public void setApplyPerson(String applyPerson) {
            this.applyPerson = applyPerson;
        }

        public String getApprovePersonName() {
            return approvePersonName;
        }

        public void setApprovePersonName(String approvePersonName) {
            this.approvePersonName = approvePersonName;
        }

        public String getUrgentLevel() {
            return urgentLevel;
        }

        public void setUrgentLevel(String urgentLevel) {
            this.urgentLevel = urgentLevel;
        }

        public String getRequestid() {
            return requestid;
        }

        public void setRequestid(String requestid) {
            this.requestid = requestid;
        }

        public String getApprovePerson() {
            return approvePerson;
        }

        public void setApprovePerson(String approvePerson) {
            this.approvePerson = approvePerson;
        }

        public String getApproveNodeId() {
            return approveNodeId;
        }

        public void setApproveNodeId(String approveNodeId) {
            this.approveNodeId = approveNodeId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.overFlag);
            dest.writeString(this.applyPersonName);
            dest.writeString(this.approveNode);
            dest.writeString(this.title);
            dest.writeString(this.applyOffWordFile);
            dest.writeString(this.applyPerson);
            dest.writeString(this.approvePersonName);
            dest.writeString(this.urgentLevel);
            dest.writeString(this.requestid);
            dest.writeString(this.approvePerson);
            dest.writeString(this.approveNodeId);
            dest.writeString(this.id);
            dest.writeString(this.createDate);
        }

        public ObjBean() {
        }

        protected ObjBean(Parcel in) {
            this.overFlag = in.readString();
            this.applyPersonName = in.readString();
            this.approveNode = in.readString();
            this.title = in.readString();
            this.applyOffWordFile = in.readString();
            this.applyPerson = in.readString();
            this.approvePersonName = in.readString();
            this.urgentLevel = in.readString();
            this.requestid = in.readString();
            this.approvePerson = in.readString();
            this.approveNodeId = in.readString();
            this.id = in.readString();
            this.createDate = in.readString();
        }

        public static final Parcelable.Creator<ObjBean> CREATOR = new Parcelable.Creator<ObjBean>() {
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
