package cn.yangchengyu.libcommon.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/3/29
 */
public class Feed extends BaseObservable implements Parcelable {

    public static final int TYPE_IMAGE_TEXT = 1;//图文
    public static final int TYPE_VIDEO = 2;//视频

    public int id;
    public long itemId;
    public int itemType;
    public long createTime;
    public double duration;
    public String feeds_text;
    public long authorId;
    public String activityIcon;
    public String activityText;
    public int width;
    public int height;
    public String url;
    public String cover;
    public User author;
    public Comment topComment;
    public Ugc ugc;

    @Bindable
    public Ugc getUgc() {
        if (ugc == null) {
            ugc = new Ugc();
        }
        return ugc;
    }

    @Bindable
    public User getAuthor() {
        return author;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Feed)) {
            return false;
        }
        Feed newFeed = (Feed) obj;
        return id == newFeed.id
                && itemId == newFeed.itemId
                && itemType == newFeed.itemType
                && createTime == newFeed.createTime
                && duration == newFeed.duration
                && TextUtils.equals(feeds_text, newFeed.feeds_text)
                && authorId == newFeed.authorId
                && TextUtils.equals(activityIcon, newFeed.activityIcon)
                && TextUtils.equals(activityText, newFeed.activityText)
                && width == newFeed.width
                && height == newFeed.height
                && TextUtils.equals(url, newFeed.url)
                && TextUtils.equals(cover, newFeed.cover)
                && (author != null && author.equals(newFeed.author))
                && (topComment != null && topComment.equals(newFeed.topComment))
                && (ugc != null && ugc.equals(newFeed.ugc));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeLong(this.itemId);
        dest.writeInt(this.itemType);
        dest.writeLong(this.createTime);
        dest.writeDouble(this.duration);
        dest.writeString(this.feeds_text);
        dest.writeLong(this.authorId);
        dest.writeString(this.activityIcon);
        dest.writeString(this.activityText);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.url);
        dest.writeString(this.cover);
        dest.writeParcelable(this.author, flags);
        dest.writeParcelable(this.topComment, flags);
        dest.writeParcelable(this.ugc, flags);
    }

    public Feed() {
    }

    protected Feed(Parcel in) {
        this.id = in.readInt();
        this.itemId = in.readLong();
        this.itemType = in.readInt();
        this.createTime = in.readLong();
        this.duration = in.readDouble();
        this.feeds_text = in.readString();
        this.authorId = in.readLong();
        this.activityIcon = in.readString();
        this.activityText = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.url = in.readString();
        this.cover = in.readString();
        this.author = in.readParcelable(User.class.getClassLoader());
        this.topComment = in.readParcelable(Comment.class.getClassLoader());
        this.ugc = in.readParcelable(Ugc.class.getClassLoader());
    }

    public static final Parcelable.Creator<Feed> CREATOR = new Parcelable.Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel source) {
            return new Feed(source);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}