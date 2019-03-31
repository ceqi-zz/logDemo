package com.test.logDemo.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final int TIMEOUT = 800;
    private static Logger LOG = LoggerFactory.getLogger(EventServiceImpl.class);
    private final RawEventRepository rawEventRepository;
    private final EventRepository eventRepository;
    private final DataService dataService;
    private final ExecutorService executorService;

    public EventServiceImpl(RawEventRepository rawEventRepository, EventRepository eventRepository, DataService dataService, ExecutorService executorService) {
        this.rawEventRepository = rawEventRepository;
        this.eventRepository = eventRepository;
        this.dataService = dataService;
        this.executorService = executorService;
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
            LOG.error("started and finished event logs are not in sync, amount not equal, skip");
        } else {
            for (int i = 0; i < finished.size(); i++) {
                RawEventDto finishedEventDto = finished.get(i);
                RawEventDto startedEventDto = started.get(i);

                Event event = createEvent(finishedEventDto, startedEventDto);
                LOG.info(String.format("Loading event to DB: %s, alert?%s, duration: %d", event.getId(), event.getAlert(), event.getDuration()));
                eventRepository.save(event);
                LOG.info("Loaded event to DB: " + event.getId());
            }
        }

    }

    @Override
    public void addAlertFlagsToAll(int sliceSize) {

        Pageable slice = dataService.getSliceSortedById(0, sliceSize);
        List<RawEventDto> finishedRawEvents = prepareRawEventsByState(slice, State.FINISHED);
        List<RawEventDto> startedRawEvents = prepareRawEventsByState(slice, State.STARTED);
        addAlertFlagsToSlice(finishedRawEvents, startedRawEvents);


        Pageable nextSlice = slice.next();

        for(int i = 0; i < rawEventRepository.count() - sliceSize; i++){
            executorService.execute(task(nextSlice));
            nextSlice = nextSlice.next();
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    private Runnable task(Pageable nextSlice){
         return () -> {
             List<RawEventDto> finishedRawEvents = prepareRawEventsByState(nextSlice, State.FINISHED);
             List<RawEventDto> startedRawEvents = prepareRawEventsByState(nextSlice, State.STARTED);
             addAlertFlagsToSlice(finishedRawEvents, startedRawEvents);
         };
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
