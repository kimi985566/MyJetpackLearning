package cn.yangchengyu.myjetpacklearning.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Destination implements Parcelable {

    private String pageUrl;
    private int id;
    private boolean needLogin;
    private boolean asStarter;
    private boolean isFragment;
    private String className;

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(boolean needLogin) {
        this.needLogin = needLogin;
    }

    public boolean isAsStarter() {
        return asStarter;
    }

    public void setAsStarter(boolean asStarter) {
        this.asStarter = asStarter;
    }

    public boolean isFragment() {
        return isFragment;
    }

    public void setFragment(boolean fragment) {
        isFragment = fragment;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pageUrl);
        dest.writeInt(this.id);
        dest.writeByte(this.needLogin ? (byte) 1 : (byte) 0);
        dest.writeByte(this.asStarter ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFragment ? (byte) 1 : (byte) 0);
        dest.writeString(this.className);
    }

    public Destination() {
    }

    protected Destination(Parcel in) {
        this.pageUrl = in.readString();
        this.id = in.readInt();
        this.needLogin = in.readByte() != 0;
        this.asStarter = in.readByte() != 0;
        this.isFragment = in.readByte() != 0;
        this.className = in.readString();
    }

    public static final Parcelable.Creator<Destination> CREATOR = new Parcelable.Creator<Destination>() {
        @Override
        public Destination createFromParcel(Parcel source) {
            return new Destination(source);
        }

        @Override
        public Destination[] newArray(int size) {
            return new Destination[size];
        }
    };
}
