package org.happiest.runners;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest
public class CucumberTestContextConfig {
    // This ensures Spring context is used for step definitions
}
