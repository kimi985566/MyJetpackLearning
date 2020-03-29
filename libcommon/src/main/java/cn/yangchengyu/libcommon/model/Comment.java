package cn.yangchengyu.libcommon.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/3/29
 */
public class Comment extends BaseObservable implements Parcelable {

    public static final int COMMENT_TYPE_IMAGE_TEXT = 2;
    public static final int COMMENT_TYPE_VIDEO = 3;

    public int id;
    public long itemId;
    public long commentId;
    public long userId;
    public int commentType;
    public long createTime;
    public int commentCount;
    public int likeCount;
    public String commentText;
    public String imageUrl;
    public String videoUrl;
    public int width;
    public int height;
    public boolean hasLiked;
    public User author;
    public Ugc ugc;

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Comment)) {
            return false;
        }

        Comment newComment = (Comment) obj;
        return likeCount == newComment.likeCount
                && hasLiked == newComment.hasLiked
                && (author != null && author.equals(newComment.author))
                && (ugc != null && ugc.equals(newComment.ugc));
    }

    public Ugc getUgc() {
        if (ugc == null) {
            ugc = new Ugc();
        }
        return ugc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeLong(this.itemId);
        dest.writeLong(this.commentId);
        dest.writeLong(this.userId);
        dest.writeInt(this.commentType);
        dest.writeLong(this.createTime);
        dest.writeInt(this.commentCount);
        dest.writeInt(this.likeCount);
        dest.writeString(this.commentText);
        dest.writeString(this.imageUrl);
        dest.writeString(this.videoUrl);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeByte(this.hasLiked ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.author, flags);
        dest.writeParcelable(this.ugc, flags);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.id = in.readInt();
        this.itemId = in.readLong();
        this.commentId = in.readLong();
        this.userId = in.readLong();
        this.commentType = in.readInt();
        this.createTime = in.readLong();
        this.commentCount = in.readInt();
        this.likeCount = in.readInt();
        this.commentText = in.readString();
        this.imageUrl = in.readString();
        this.videoUrl = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.hasLiked = in.readByte() != 0;
        this.author = in.readParcelable(User.class.getClassLoader());
        this.ugc = in.readParcelable(Ugc.class.getClassLoader());
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}