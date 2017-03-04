package com.jingyu.test.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jingyu on 2017/3/3.
 */

public class AIDLBean implements Parcelable {

    String name;
    int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AIDLBean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}' + hashCode();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
    }

    //默认生成的Parcelable 模板类的对象只支持为 in 的定向 tag 。即默认生成的类里面的 writeToParcel() 方法，
    //而如果要支持为 out 或者 inout 的定向 tag 的话，还需要实现 readFromParcel() 方法——而这个方法其实并没有在
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        name = dest.readString();
        id = dest.readInt();
    }

    public AIDLBean() {
    }

    protected AIDLBean(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<AIDLBean> CREATOR = new Creator<AIDLBean>() {
        @Override
        public AIDLBean createFromParcel(Parcel source) {
            return new AIDLBean(source);
        }

        @Override
        public AIDLBean[] newArray(int size) {
            return new AIDLBean[size];
        }
    };
}
