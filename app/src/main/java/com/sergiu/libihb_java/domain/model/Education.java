package com.sergiu.libihb_java.domain.model;

import java.util.List;

public class Education {
    private String inCaseOf;
    private List<String> doList;
    private List<String> doNotList;
    private List<String> preventList;

    public Education(
            String inCaseOf,
            List<String> doList,
            List<String> doNotList,
            List<String> preventList) {
        this.inCaseOf = inCaseOf;
        this.doList = doList;
        this.doNotList = doNotList;
        this.preventList = preventList;
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
}
