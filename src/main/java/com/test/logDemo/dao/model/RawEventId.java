package com.test.logDemo.dao.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RawEventId implements Serializable {
    @Column(name="id")
    private String id;
    @Column(name="state")
    private String state;

    public RawEventId(){

    }

    public RawEventId(String id, String state) {
        this.id = id;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RawEventId that = (RawEventId) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state);
    }

    @Override
    public String toString() {
        return "RawEventId{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
