package com.mobilepolice.office.bean;

import java.util.List;

public class ApproveDetails {

    /**
     * msg : 操作成功
     * obj : {"departmentName":"秘书科","schema":"1","requestNum":"1","img":"http://www.freetk.cn:8789/mobileworkws-image/document/292D6F56B5FE4C23991E2529762CBA57-110754.jpg","applyPersonName":"钱科员","secretLevel":"3","applyPerson":"110754","titel":"标题1","pdfFile":"","urgentLevel":"1","id":"292D6F56B5FE4C23991E2529762CBA57","department":"1101","requestFlag":"通知","createDate":"2019-03-08 16:53:21"}
     * success : true
     * attributes : {"approveNodeId":"1","notApprovePerson":[{"approvePersonName":"赵科长","approvePerson":"100935"}],"approveListInfo":[]}
     */

    private String msg;
    private ObjBean obj;
    private boolean success;
    private AttributesBean attributes;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public AttributesBean getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesBean attributes) {
        this.attributes = attributes;
    }

    public static class ObjBean {
        /**
         * departmentName : 秘书科
         * schema : 1
         * requestNum : 1
         * img : http://www.freetk.cn:8789/mobileworkws-image/document/292D6F56B5FE4C23991E2529762CBA57-110754.jpg
         * applyPersonName : 钱科员
         * secretLevel : 3
         * applyPerson : 110754
         * titel : 标题1
         * pdfFile :
         * urgentLevel : 1
         * id : 292D6F56B5FE4C23991E2529762CBA57
         * department : 1101
         * requestFlag : 通知
         * createDate : 2019-03-08 16:53:21
         */

        private String departmentName;
        private String schema;
        private String requestNum;
        private String img;
        private String applyPersonName;
        private String secretLevel;
        private String applyPerson;
        private String titel;
        private String pdfFile;
        private String urgentLevel;
        private String id;
        private String department;
        private String requestFlag;
        private String createDate;

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }

        public String getRequestNum() {
            return requestNum;
        }

        public void setRequestNum(String requestNum) {
            this.requestNum = requestNum;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getApplyPersonName() {
            return applyPersonName;
        }

        public void setApplyPersonName(String applyPersonName) {
            this.applyPersonName = applyPersonName;
        }

        public String getSecretLevel() {
            return secretLevel;
        }

        public void setSecretLevel(String secretLevel) {
            this.secretLevel = secretLevel;
        }

        public String getApplyPerson() {
            return applyPerson;
        }

        public void setApplyPerson(String applyPerson) {
            this.applyPerson = applyPerson;
        }

        public String getTitel() {
            return titel;
        }

        public void setTitel(String titel) {
            this.titel = titel;
        }

        public String getPdfFile() {
            return pdfFile;
        }

        public void setPdfFile(String pdfFile) {
            this.pdfFile = pdfFile;
        }

        public String getUrgentLevel() {
            return urgentLevel;
        }

        public void setUrgentLevel(String urgentLevel) {
            this.urgentLevel = urgentLevel;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getRequestFlag() {
            return requestFlag;
        }

        public void setRequestFlag(String requestFlag) {
            this.requestFlag = requestFlag;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }

    public static class AttributesBean {
        /**
         * approveNodeId : 1
         * notApprovePerson : [{"approvePersonName":"赵科长","approvePerson":"100935"}]
         * approveListInfo : []
         */

        private String approveNodeId;
        private List<NotApprovePersonBean> notApprovePerson;
        private List<?> approveListInfo;

        public String getApproveNodeId() {
            return approveNodeId;
        }

        public void setApproveNodeId(String approveNodeId) {
            this.approveNodeId = approveNodeId;
        }

        public List<NotApprovePersonBean> getNotApprovePerson() {
            return notApprovePerson;
        }

        public void setNotApprovePerson(List<NotApprovePersonBean> notApprovePerson) {
            this.notApprovePerson = notApprovePerson;
        }

        public List<?> getApproveListInfo() {
            return approveListInfo;
        }

        public void setApproveListInfo(List<?> approveListInfo) {
            this.approveListInfo = approveListInfo;
        }

        public static class NotApprovePersonBean {
            /**
             * approvePersonName : 赵科长
             * approvePerson : 100935
             */

            private String approvePersonName;
            private String approvePerson;

            public String getApprovePersonName() {
                return approvePersonName;
            }

            public void setApprovePersonName(String approvePersonName) {
                this.approvePersonName = approvePersonName;
            }

            public String getApprovePerson() {
                return approvePerson;
            }

            public void setApprovePerson(String approvePerson) {
                this.approvePerson = approvePerson;
            }
        }
    }
}
