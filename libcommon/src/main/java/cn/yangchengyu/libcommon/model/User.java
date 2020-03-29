package cn.yangchengyu.libcommon.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.util.Objects;

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/3/23
 */
public class User extends BaseObservable implements Parcelable {

    /**
     * id : 962
     * userId : 3223400206308231
     * name : 二师弟请随我来
     * avatar :
     * description :
     * likeCount : 0
     * topCommentCount : 0
     * followCount : 0
     * followerCount : 0
     * qqOpenId : null
     * expires_time : 0
     * score : 0
     * historyCount : 0
     * commentCount : 0
     * favoriteCount : 0
     * feedCount : 0
     * hasFollow : false
     */

    public int id;
    public long userId;
    public String name;
    public String avatar;
    public String description;
    public int likeCount;
    public int topCommentCount;
    public int followCount;
    public int followerCount;
    public String qqOpenId;
    public long expires_time;
    public int score;
    public int historyCount;
    public int commentCount;
    public int favoriteCount;
    public int feedCount;
    public boolean hasFollow;

    @Bindable
    public boolean isHasFollow() {
        return hasFollow;
    }

    public void setHasFollow(boolean hasFollow) {
        this.hasFollow = hasFollow;
        notifyPropertyChanged(BR._all);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeLong(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeString(this.description);
        dest.writeInt(this.likeCount);
        dest.writeInt(this.topCommentCount);
        dest.writeInt(this.followCount);
        dest.writeInt(this.followerCount);
        dest.writeString(this.qqOpenId);
        dest.writeLong(this.expires_time);
        dest.writeInt(this.score);
        dest.writeInt(this.historyCount);
        dest.writeInt(this.commentCount);
        dest.writeInt(this.favoriteCount);
        dest.writeInt(this.feedCount);
        dest.writeByte(this.hasFollow ? (byte) 1 : (byte) 0);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.userId = in.readLong();
        this.name = in.readString();
        this.avatar = in.readString();
        this.description = in.readString();
        this.likeCount = in.readInt();
        this.topCommentCount = in.readInt();
        this.followCount = in.readInt();
        this.followerCount = in.readInt();
        this.qqOpenId = in.readString();
        this.expires_time = in.readLong();
        this.score = in.readInt();
        this.historyCount = in.readInt();
        this.commentCount = in.readInt();
        this.favoriteCount = in.readInt();
        this.feedCount = in.readInt();
        this.hasFollow = in.readByte() != 0;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id == user.id &&
                userId == user.userId &&
                likeCount == user.likeCount &&
                topCommentCount == user.topCommentCount &&
                followCount == user.followCount &&
                followerCount == user.followerCount &&
                expires_time == user.expires_time &&
                score == user.score &&
                historyCount == user.historyCount &&
                commentCount == user.commentCount &&
                favoriteCount == user.favoriteCount &&
                feedCount == user.feedCount &&
                isHasFollow() == user.isHasFollow() &&
                Objects.equals(name, user.name) &&
                Objects.equals(avatar, user.avatar) &&
                Objects.equals(description, user.description) &&
                Objects.equals(qqOpenId, user.qqOpenId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, name, avatar, description, likeCount, topCommentCount, followCount, followerCount, qqOpenId, expires_time, score, historyCount, commentCount, favoriteCount, feedCount, isHasFollow());
    }
}
