package com.mobilepolice.office.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.mobilepolice.office.utils.DateUtil;

import java.util.Collections;
import java.util.List;

public class DraftsEmailList {

    /**
     * msg : 查询成功
     * obj : [{"policeNumber":"123456","receiver":"","id":"32A213D711DA4ACB848AB4053192A001","title":"ddffgg?content=cfutcftctfcfcdxd?receiver=cuinan@gat.jl","content":"","createDate":"2019-03-21 15:22:41"},{"policeNumber":"123456","receiver":"","id":"302DC110C32648AA9E93874D05FC2E52","title":"dxxccfgvv?content=xderfggh?receiver=cuinan@gat.jl","content":"","createDate":"2019-03-21 15:57:49"},{"policeNumber":"123456","receiver":"","id":"2C1C7675C53545ACA5221A8C29C7E852","title":"cfcycfycf?content=cgycgycginiCG一吹?receiver=cuinan@gat.jl","content":"","createDate":"2019-03-21 16:03:00"},{"policeNumber":"123456","receiver":"","id":"78CE3AD3A31444E09C7CAB977FEF6F6B","title":"v过一次观察观察?content=吃饭呀穿衣吃饭吃药?receiver=cuinan@gat.jl","content":"","createDate":"2019-03-21 16:49:01"},{"policeNumber":"123456","receiver":"","id":"413A7A941C9C4F0DBC64357933CB588A","title":"null?content=null?receiver=null","content":"","createDate":"2019-03-21 18:48:28"},{"policeNumber":"123456","receiver":"","id":"35B9DB7D38044F0E84B7F7276ABBA346","title":"?content=?receiver=null","content":"","createDate":"2019-03-21 18:49:35"},{"policeNumber":"123456","receiver":"","id":"1DB2BAD32C754A8087E9419911443EC4","title":"?content=?receiver=null","content":"","createDate":"2019-03-21 18:34:47"},{"policeNumber":"123456","receiver":"","id":"CF6853118F624744A17E0B50BF13A247","title":"?content=?receiver=null","content":"","createDate":"2019-03-21 18:59:50"},{"policeNumber":"123456","receiver":"","id":"03F74DB83A444A02A2BDC3B5C8774CF9","title":"?content=?receiver=null","content":"","createDate":"2019-03-21 18:19:53"},{"policeNumber":"123456","receiver":"","id":"7964107D07834539AAB3B4D37FD50717","title":"?content=?receiver=null","content":"","createDate":"2019-03-21 18:23:55"},{"policeNumber":"123456","receiver":"","id":"6F3814192FA341AAA249883A42075D50","title":"?content=?receiver=null","content":"","createDate":"2019-03-21 18:08:57"},{"policeNumber":"123456","receiver":"","id":"A1A90D35595249ACB2FA5E37250C2305","title":"?content=?receiver=null","content":"","createDate":"2019-03-21 19:35:00"},{"policeNumber":"123456","receiver":"","id":"3897C60B2EC145E689E5CEB4A9745119","title":"?content=?receiver=null","content":"","createDate":"2019-03-21 19:32:08"},{"policeNumber":"123456","receiver":"","id":"7DC595563B0F4C8A82DA87E98B5E5F3F","title":"城管局查干湖唱歌?content=初伏CG?receiver=cuinan@gat.jl","content":"","createDate":"2019-03-21 19:48:11"},{"policeNumber":"123456","receiver":"","id":"9A6DD801AA804B7CAAF1ED948307FEA7","title":"查干湖查干湖错过火车?content=出成绩唱歌?receiver=cuinan@gat.jl","content":"","createDate":"2019-03-21 19:08:13"},{"policeNumber":"123456","receiver":"","id":"4C7153C0A2214A3FB863C12554039CEE","title":"差点迟到c?content=吃大餐动次打次?receiver=cuinan@gat.jl","content":"","createDate":"2019-03-22 08:06:44"},{"policeNumber":"123456","receiver":"","id":"9DED418F481F414287FD82304A923FAE","title":"?content=?receiver=null","content":"","createDate":"2019-03-22 09:09:04"}]
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

    public static class ObjBean implements Parcelable,Comparable<ObjBean> {

        /**
         * policeNumber : 123456
         * receiver :
         * id : 32A213D711DA4ACB848AB4053192A001
         * title : ddffgg?content=cfutcftctfcfcdxd?receiver=cuinan@gat.jl
         * content :
         * createDate : 2019-03-21 15:22:41
         */

        private String policeNumber;
        private String receiver;
        private String id;
        private String title;
        private String content;
        private String createDate;

        public String getPoliceNumber() {
            return policeNumber;
        }

        public void setPoliceNumber(String policeNumber) {
            this.policeNumber = policeNumber;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
            dest.writeString(this.policeNumber);
            dest.writeString(this.receiver);
            dest.writeString(this.id);
            dest.writeString(this.title);
            dest.writeString(this.content);
            dest.writeString(this.createDate);
        }

        public ObjBean() {
        }

        protected ObjBean(Parcel in) {
            this.policeNumber = in.readString();
            this.receiver = in.readString();
            this.id = in.readString();
            this.title = in.readString();
            this.content = in.readString();
            this.createDate = in.readString();
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

        @Override
        public int compareTo(@NonNull DraftsEmailList.ObjBean o) {
            if ( DateUtil.parseDate("yyyy-MM-dd HH:mm:ss",createDate)> DateUtil.parseDate("yyyy-MM-dd HH:mm:ss",o.createDate))
                return -1;
            else if ( DateUtil.parseDate("yyyy-MM-dd HH:mm:ss",createDate)<DateUtil.parseDate("yyyy-MM-dd HH:mm:ss",o.createDate)){
                return 1;
            }
            return 0;
        }

    }
}
