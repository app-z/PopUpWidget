package com.renal128.demo.recyclerviewdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Record implements Parcelable {

    private long id;

    public Record() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public enum Type {RED, GREEN, YELLOW}

    private String name;
    private Type type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Record copy() {
        Record copy = new Record();
        copy.setType(type);
        copy.setName(name);
        copy.setId(id);
        return copy;
    }

    protected Record(Parcel in) {
        name = in.readString();
        type = (Type) in.readValue(Type.class.getClassLoader());
        id = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeValue(type);
        dest.writeLong(id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Record> CREATOR = new Parcelable.Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };
}