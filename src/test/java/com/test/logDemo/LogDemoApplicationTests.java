package com.test.logDemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = LogDemoApplication.class,
		initializers = ConfigFileApplicationContextInitializer.class)
public class LogDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
