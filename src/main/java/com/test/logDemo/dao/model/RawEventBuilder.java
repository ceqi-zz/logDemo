package com.test.logDemo.dao.model;

public class RawEventBuilder {
    private RawEventId id;
    private String type;
    private String host;
    private Long timestamp;

    public RawEventBuilder setId(RawEventId id) {
        this.id = id;
        return this;
    }

    public RawEventBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public RawEventBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public RawEventBuilder setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public RawEvent createRawEvent() {
        return new RawEvent(id, type, host, timestamp);
    }
}