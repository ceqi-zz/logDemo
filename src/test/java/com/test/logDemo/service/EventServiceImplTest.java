package com.test.logDemo.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.logDemo.LogDemoApplication;
import com.test.logDemo.dao.model.RawEventBuilder;
import com.test.logDemo.dao.model.RawEventId;
import com.test.logDemo.dao.repository.EventRepository;
import com.test.logDemo.dao.repository.RawEventRepository;
import com.test.logDemo.dto.RawEventDto;
import com.test.logDemo.util.State;

import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = LogDemoApplication.class,
        initializers = ConfigFileApplicationContextInitializer.class)
public class EventServiceImplTest {

    @Autowired
    private EventService eventService;
    @Autowired
    private RawEventRepository rawEventRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private DataService dataService;

    @Before
    public void setUp() {
        rawEventRepository.save(new RawEventBuilder().setId(new RawEventId("scsmbstgra", "STARTED")).setType("APPLICATION_LOG").setHost("12345").setTimestamp(1491377495212L).createRawEvent());
        rawEventRepository.save(new RawEventBuilder().setId(new RawEventId("scsmbstgrb", "STARTED")).setType(null).setHost(null).setTimestamp(1491377495213L).createRawEvent());
        rawEventRepository.save(new RawEventBuilder().setId(new RawEventId("scsmbstgrc", "FINISHED")).setType(null).setHost(null).setTimestamp(1491377495218L).createRawEvent());
        rawEventRepository.save(new RawEventBuilder().setId(new RawEventId("scsmbstgra", "FINISHED")).setType("APPLICATION_LOG").setHost("12345").setTimestamp(1491377495217L).createRawEvent());
        rawEventRepository.save(new RawEventBuilder().setId(new RawEventId("scsmbstgrc", "STARTED")).setType(null).setHost(null).setTimestamp(1491377495210L).createRawEvent());
        rawEventRepository.save(new RawEventBuilder().setId(new RawEventId("scsmbstgrb", "FINISHED")).setType(null).setHost(null).setTimestamp(1491377495216L).createRawEvent());
    }

    @After
    public void tearDown() {
        rawEventRepository.deleteAll();
    }

    @Test
    public void addAlertFlags_thenAlertsAreAddedToLongLastingEvents() {
        int sliceSize = 2;
        Pageable slice = dataService.getSliceSortedById(0, sliceSize);
        List<RawEventDto> finished = eventService.prepareRawEventsByState(slice, State.FINISHED);
        List<RawEventDto> started = eventService.prepareRawEventsByState(slice, State.STARTED);
        eventService.addAlertFlagsToSlice(finished, started);

        assertThat(eventRepository.findById("scsmbstgra").get().getAlert()).isEqualTo(true);

    }

    @Test
    public void addAlertFlagsToAll_thenAllEventsAreAdded() {
        eventService.addAlertFlagsToAll(2);
        assertThat(eventRepository.count()).isEqualTo(3);
    }

}