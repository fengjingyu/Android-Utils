package com.jingyu.test.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jingyu on 2017/3/3.
 */

public class AIDLBean implements Parcelable {

    String name;
    int age;
    String address;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeString(this.address);
    }

    public AIDLBean() {
    }

    public AIDLBean(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.address = in.readString();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AIDLBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
