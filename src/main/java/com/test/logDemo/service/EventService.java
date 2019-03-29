package com.test.logDemo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.test.logDemo.dto.RawEventDto;
import com.test.logDemo.util.State;

public interface EventService {
    List<RawEventDto> prepareRawEventsByState(Pageable slice, State state);
    void addAlertFlagsToSlice(List<RawEventDto> finished, List<RawEventDto> started);
    void addAlertFlagsToAll(int sliceSize);
}
