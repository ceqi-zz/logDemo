package com.test.logDemo.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.logDemo.dao.model.Event;

@Repository
public interface EventRepository extends CrudRepository<Event,String> {
}
