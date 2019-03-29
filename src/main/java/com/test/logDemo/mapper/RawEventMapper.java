package com.test.logDemo.mapper;

import org.springframework.stereotype.Component;

import com.test.logDemo.dao.model.RawEvent;
import com.test.logDemo.dao.model.RawEventBuilder;
import com.test.logDemo.dao.model.RawEventId;
import com.test.logDemo.dto.RawEventDto;
import com.test.logDemo.dto.RawEventDtoBuilder;

@Component
public class RawEventMapper {
    public static  RawEvent dtoToRawEventMapper(RawEventDto dto){
        RawEventId rawEventId = new RawEventId(dto.getId(), dto.getState());
        return new RawEventBuilder()
                .setId(rawEventId)
                .setType(dto.getType())
                .setHost(dto.getHost())
                .setTimestamp(dto.getTimestamp())
                .createRawEvent();
    }

    public static RawEventDto rawEventToDtoMapper(RawEvent event){
        RawEventDto rawEventDto = new RawEventDto();
        rawEventDto.setId(event.getId().getId());
        rawEventDto.setState(event.getId().getState());
        rawEventDto.setHost(event.getHost());
        rawEventDto.setType(event.getType());
        rawEventDto.setTimestamp(event.getTimestamp());
        return rawEventDto;
    }
}
