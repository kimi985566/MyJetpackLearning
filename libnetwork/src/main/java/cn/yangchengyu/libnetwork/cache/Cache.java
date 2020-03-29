package cn.yangchengyu.libnetwork.cache;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cache")
public class Cache implements Parcelable {

    /**
     * PrimaryKey 必须要有,且不为空,autoGenerate 主键的值是否由Room自动生成,默认false
     */
    @NonNull
    @PrimaryKey(autoGenerate = false)
    public String key = "";

    public byte[] data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeByteArray(this.data);
    }

    public Cache() {
    }

    protected Cache(Parcel in) {
        this.key = in.readString();
        this.data = in.createByteArray();
    }

    public static final Parcelable.Creator<Cache> CREATOR = new Parcelable.Creator<Cache>() {
        @Override
        public Cache createFromParcel(Parcel source) {
            return new Cache(source);
        }

        @Override
        public Cache[] newArray(int size) {
            return new Cache[size];
        }
    };
}

