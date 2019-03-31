package com.test.logDemo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import com.test.logDemo.LogDemoApplication;
import com.test.logDemo.dao.model.RawEvent;
import com.test.logDemo.dao.model.RawEventBuilder;
import com.test.logDemo.dao.model.RawEventId;
import com.test.logDemo.dao.repository.RawEventRepository;
import com.test.logDemo.util.State;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = LogDemoApplication.class,
        initializers = ConfigFileApplicationContextInitializer.class)
public class DataServiceImplTest {
    @Autowired
    private RawEventRepository rawEventRepository;
    @Autowired
    private DataService dataService;

    @Before
    public void setup() {
        rawEventRepository.save(new RawEventBuilder().setId(new RawEventId("scsmbstgra", "STARTED")).setType("APPLICATION_LOG").setHost("12345").setTimestamp(1491377495212L).createRawEvent());
        rawEventRepository.save(new RawEventBuilder().setId(new RawEventId("scsmbstgrb", "STARTED")).setType(null).setHost(null).setTimestamp(1491377495213L).createRawEvent());
        rawEventRepository.save(new RawEventBuilder().setId(new RawEventId("scsmbstgrc", "FINISHED")).setType(null).setHost(null).setTimestamp(1491377495218L).createRawEvent());
        rawEventRepository.save(new RawEventBuilder().setId(new RawEventId("scsmbstgra", "FINISHED")).setType("APPLICATION_LOG").setHost("12345").setTimestamp(1491377495217L).createRawEvent());
        rawEventRepository.save(new RawEventBuilder().setId(new RawEventId("scsmbstgrc", "STARTED")).setType(null).setHost(null).setTimestamp(1491377495210L).createRawEvent());
        rawEventRepository.save(new RawEventBuilder().setId(new RawEventId("scsmbstgrb", "FINISHED")).setType(null).setHost(null).setTimestamp(1491377495216L).createRawEvent());
    }

    @After
    public void teardown() {
        rawEventRepository.deleteAll();
    }

    @Test
    public void whenGettingRawEventSlice_thenLimitedRowsReturned() {

        int page = 0;
        int size = 2;
        String started = State.STARTED.get();
        Pageable slice = dataService.getSliceSortedById(page, size);
        List<RawEvent> resultList = rawEventRepository.findAllById_State(started, slice);
        assertThat(resultList.size()).isEqualTo(size);

    }

    @Test
    public void whenLoadFromFile_thenDataIsLoadedToDb() throws FileNotFoundException {
        rawEventRepository.deleteAll();
        String filePath = ResourceUtils.getFile("classpath:data.txt").getPath();
        dataService.loadFromFile(filePath);
        assertThat(rawEventRepository.count()).isEqualTo(2);
    }

}