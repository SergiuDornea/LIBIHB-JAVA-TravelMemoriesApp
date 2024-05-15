package com.sergiu.libihb_java.domain.model;

public class Education {
    private final String id;
    private final String inCaseOf;
    private final String doThis;
    private final String doNot;
    private final String prevent;

    public Education(String id, String inCaseOf, String doThis, String doNot, String prevent) {
        this.id = id;
        this.inCaseOf = inCaseOf;
        this.doThis = doThis;
        this.doNot = doNot;
        this.prevent = prevent;
    }

    public String getId() {
        return id;
    }

    public String getInCaseOf() {
        return inCaseOf;
    }

    public String getDoThis() {
        return doThis;
    }

    public String getDoNot() {
        return doNot;
    }

    public String getPrevent() {
        return prevent;
    }
}
