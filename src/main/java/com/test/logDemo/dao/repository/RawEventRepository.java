package com.test.logDemo.dao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.test.logDemo.dao.model.RawEvent;
import com.test.logDemo.dao.model.RawEventId;

@Repository
public interface RawEventRepository extends PagingAndSortingRepository<RawEvent, RawEventId> {
    List<RawEvent> findAllById_State(String state, Pageable nextSlice);

}