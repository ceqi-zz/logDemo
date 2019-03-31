package com.test.logDemo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.logDemo.dao.model.RawEvent;
import com.test.logDemo.dao.repository.RawEventRepository;
import com.test.logDemo.dto.RawEventDto;
import com.test.logDemo.mapper.RawEventMapper;

@Service
public class DataServiceImpl implements DataService {
    private static Logger LOG  = LoggerFactory.getLogger(DataServiceImpl.class);

    private final ObjectMapper objectMapper;
    private final RawEventRepository rawEventRepository;

    public DataServiceImpl(ObjectMapper objectMapper, RawEventRepository rawEventRepository) {
        this.objectMapper = objectMapper;
        this.rawEventRepository = rawEventRepository;
    }

    @Override
    public Pageable getSliceSortedById(int page, int size) {
        return PageRequest.of(page,size, Sort.by("id"));
    }

    @Override
    public void loadFromFile(String filePath) {
        LOG.info("loading from file:" + filePath);
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(event -> {
                try {
                    RawEventDto rawEventDto = objectMapper.readValue(event, RawEventDto.class);
                    RawEvent rawEvent = RawEventMapper.dtoToRawEventMapper(rawEventDto);
                    LOG.info("Loading raw event to DB: " + rawEvent.getId().toString());
                    rawEventRepository.save(rawEvent);
                    LOG.info("Loaded raw event to DB: " + rawEvent.getId().toString());
                } catch (IOException e) {
                    LOG.error("Error reading event string: Unmarshalling error");
                }
            });

        } catch (IOException e) {
            LOG.error("Error reading event string: I/O error");
        }


    }

}
