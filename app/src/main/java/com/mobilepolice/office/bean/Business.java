package com.mobilepolice.office.bean;

/**
 * 业务
 */
public class Business {
    private String name;//姓名
    private String type;//类别 0请假 1报销
    private String time;//时间
    private String state;//状态
    private Leave leave;//请假
    private Account account;//报销

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Leave getLeave() {
        return leave;
    }

    public void setLeave(Leave leave) {
        this.leave = leave;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * 请假
     */
    public class Leave {
        private String duration;//时长
        private String reason;//事由
        private String LeaveTime;//请假时间

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getLeaveTime() {
            return LeaveTime;
        }

        public void setLeaveTime(String leaveTime) {
            LeaveTime = leaveTime;
        }

    }

    /**
     * 报销
     */
    public class Account {
        private String money;//金额
        private String reason;//事由
        private String proof;//凭证

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getProof() {
            return proof;
        }

        public void setProof(String proof) {
            this.proof = proof;
        }


    }


}
