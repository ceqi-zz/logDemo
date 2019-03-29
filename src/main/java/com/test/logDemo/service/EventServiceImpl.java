package com.test.logDemo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.logDemo.dao.model.Event;
import com.test.logDemo.dao.model.RawEvent;
import com.test.logDemo.dao.repository.EventRepository;
import com.test.logDemo.dao.repository.RawEventRepository;
import com.test.logDemo.dto.RawEventDto;
import com.test.logDemo.mapper.RawEventMapper;
import com.test.logDemo.util.State;

@Service
public class EventServiceImpl implements EventService {
    private static final int DURATION_THRESHOLD = 4;
    private static Logger LOG = LoggerFactory.getLogger(EventServiceImpl.class);
    private final RawEventRepository rawEventRepository;
    private final EventRepository eventRepository;
    private final DataService dataService;

    public EventServiceImpl(RawEventRepository rawEventRepository, EventRepository eventRepository, DataService dataService) {
        this.rawEventRepository = rawEventRepository;
        this.eventRepository = eventRepository;
        this.dataService = dataService;
    }

    @Override
    public List<RawEventDto> prepareRawEventsByState(Pageable slice, State state) {
        List<RawEvent> rawEvents = rawEventRepository.findAllById_State(state.get(), slice);
        return rawEvents.stream()
                .map(RawEventMapper::rawEventToDtoMapper)
                .collect(Collectors.toList());
    }

    @Override
    public void addAlertFlagsToSlice(List<RawEventDto> finished, List<RawEventDto> started) {
        if (finished.size() != started.size()) {
            LOG.error("started and finished event logs are not equal, skip");
        } else {
            for (int i = 0; i < finished.size(); i++) {
                RawEventDto finishedEventDto = finished.get(i);
                RawEventDto startedEventDto = started.get(i);

                Event event = createEvent(finishedEventDto, startedEventDto);

                eventRepository.save(event);

            }
        }

    }

    @Override
    public void addAlertFlagsToAll(int sliceSize) {

        Pageable slice = dataService.getSliceSortedById(0, sliceSize);
        List<RawEventDto> finished = prepareRawEventsByState(slice, State.FINISHED);
        List<RawEventDto> started = prepareRawEventsByState(slice, State.STARTED);
        addAlertFlagsToSlice(finished, started);


        Pageable nextSlice = slice.next();

        for(int i = 0; i< rawEventRepository.count() - sliceSize; i++){
            finished = prepareRawEventsByState(nextSlice, State.FINISHED);
            started = prepareRawEventsByState(nextSlice, State.STARTED);
            addAlertFlagsToSlice(finished, started);

            nextSlice = nextSlice.next();
        }
    }

    private Event createEvent(RawEventDto finishedEventDto, RawEventDto startedEventDto) {
        Event event = new Event();
        event.setId(finishedEventDto.getId());
        event.setHost(finishedEventDto.getHost());
        event.setType(finishedEventDto.getType());
        int duration = (int) (finishedEventDto.getTimestamp() - startedEventDto.getTimestamp());
        event.setDuration(duration);
        if (duration > DURATION_THRESHOLD) {
            event.setAlert(true);
        }
        return event;
    }

}
