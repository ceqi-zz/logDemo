package com.test.logDemo.util;

public enum State {
    STARTED("STARTED"),
    FINISHED("FINISHED");

    private String state;

    State(String state) {
        this.state = state;
    }

    public String get() {
        return state;
    }


}
