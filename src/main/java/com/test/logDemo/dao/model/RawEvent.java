package com.test.logDemo.dao.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RAW_EVENTS")
public class RawEvent {

    @Id
    private RawEventId id;
    private String type;
    private String host;
    private Long timestamp;

    public RawEvent(){

    }

    public RawEvent(RawEventId id, String type, String host, Long timestamp) {
        this.id = id;
        this.type = type;
        this.host = host;
        this.timestamp = timestamp;
    }

    public RawEventId getId() {
        return id;
    }

    public void setId(RawEventId id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RawEvent rawEvent = (RawEvent) o;
        return Objects.equals(id, rawEvent.id) &&
                Objects.equals(type, rawEvent.type) &&
                Objects.equals(host, rawEvent.host) &&
                Objects.equals(timestamp, rawEvent.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, host, timestamp);
    }

    @Override
    public String toString() {
        return "RawEvent{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
