package com.test.logDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.test.logDemo.service.DataService;
import com.test.logDemo.service.EventService;

@SpringBootApplication
public class LogDemoApplication implements CommandLineRunner {
	private static Logger LOG  = LoggerFactory.getLogger(LogDemoApplication.class);

	@Autowired
	private DataService dataService;
	@Autowired
	private EventService eventService;

	private static final int SLICE_SIZE = 10000;

	public static void main(String[] args) {
		SpringApplication.run(LogDemoApplication.class, args);

	}

	@Override
	public void run(String... args) {
		LOG.info("EXECUTING: log demo");
		LOG.info("Load file:");
		dataService.loadFromFile(args[0]);
		LOG.info("File loaded to hsqldb");
		LOG.info("Process events, add alert");
		eventService.addAlertFlagsToAll(SLICE_SIZE);
		LOG.info("FINISHED: log demo");


	}

}


