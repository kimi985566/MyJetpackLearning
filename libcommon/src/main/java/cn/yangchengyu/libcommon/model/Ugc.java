package cn.yangchengyu.libcommon.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.io.Serializable;
import java.util.Objects;

public class Ugc extends BaseObservable implements Parcelable, Serializable {

    public int likeCount;
    public int shareCount;
    public int commentCount;
    public boolean hasFavorite;
    public boolean hasdiss;
    public boolean hasLiked;

    @Bindable
    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
        notifyPropertyChanged(BR._all);
    }

    @Bindable
    public boolean isHasdiss() {
        return hasdiss;
    }

    public void setHasdiss(boolean hasdiss) {
        if (this.hasdiss == hasdiss) {
            return;
        }
        if (hasdiss) {
            setHasLiked(false);
        }
        this.hasdiss = hasdiss;
        notifyPropertyChanged(BR._all);
    }

    @Bindable
    public boolean isHasLiked() {
        return hasLiked;
    }

    public void setHasLiked(boolean hasLiked) {
        if (this.hasLiked == hasLiked) {
            return;
        }
        if (hasLiked) {
            likeCount = likeCount + 1;
            setHasdiss(false);
        } else {
            likeCount = likeCount - 1;
        }
        this.hasLiked = hasLiked;
        notifyPropertyChanged(BR._all);
    }

    @Bindable
    public boolean isHasFavorite() {
        return hasFavorite;
    }

    public void setHasFavorite(boolean hasFavorite) {
        this.hasFavorite = hasFavorite;
        notifyPropertyChanged(BR._all);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ugc)) {
            return false;
        }
        Ugc ugc = (Ugc) o;
        return likeCount == ugc.likeCount &&
                getShareCount() == ugc.getShareCount() &&
                commentCount == ugc.commentCount &&
                isHasFavorite() == ugc.isHasFavorite() &&
                isHasdiss() == ugc.isHasdiss() &&
                isHasLiked() == ugc.isHasLiked();
    }

    @Override
    public int hashCode() {
        return Objects.hash(likeCount, getShareCount(), commentCount, isHasFavorite(), isHasdiss(), isHasLiked());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.likeCount);
        dest.writeInt(this.shareCount);
        dest.writeInt(this.commentCount);
        dest.writeByte(this.hasFavorite ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasdiss ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasLiked ? (byte) 1 : (byte) 0);
    }

    public Ugc() {
    }

    protected Ugc(Parcel in) {
        this.likeCount = in.readInt();
        this.shareCount = in.readInt();
        this.commentCount = in.readInt();
        this.hasFavorite = in.readByte() != 0;
        this.hasdiss = in.readByte() != 0;
        this.hasLiked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Ugc> CREATOR = new Parcelable.Creator<Ugc>() {
        @Override
        public Ugc createFromParcel(Parcel source) {
            return new Ugc(source);
        }

        @Override
        public Ugc[] newArray(int size) {
            return new Ugc[size];
        }
    };
}