package com.sergiu.libihb_java.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Education implements Parcelable {
    private String id;
    private String inCaseOf;
    private List<String> doList;
    private List<String> doNotList;
    private List<String> preventList;

    public Education(
            String id,
            String inCaseOf,
            List<String> doList,
            List<String> doNotList,
            List<String> preventList) {
        this.id = id;
        this.inCaseOf = inCaseOf;
        this.doList = doList;
        this.doNotList = doNotList;
        this.preventList = preventList;
    }

    protected Education(Parcel in) {
        id = in.readString();
        inCaseOf = in.readString();
        doList = in.createStringArrayList();
        doNotList = in.createStringArrayList();
        preventList = in.createStringArrayList();
    }

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel in) {
            return new Education(in);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getInCaseOf() {
        return inCaseOf;
    }

    public void setInCaseOf(String inCaseOf) {
        this.inCaseOf = inCaseOf;
    }

    public List<String> getDoList() {
        return doList;
    }

    public void setDoList(List<String> doList) {
        this.doList = doList;
    }

    public List<String> getDoNotList() {
        return doNotList;
    }

    public void setDoNotList(List<String> doNotList) {
        this.doNotList = doNotList;
    }

    public List<String> getPreventList() {
        return preventList;
    }

    public void setPreventList(List<String> preventList) {
        this.preventList = preventList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(inCaseOf);
        dest.writeStringList(doList);
        dest.writeStringList(doNotList);
        dest.writeStringList(preventList);
    }
}
