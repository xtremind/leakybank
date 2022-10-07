package fr.ing.secu.leakybank.configuration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import fr.ing.secu.leakybank.Application;
import io.cucumber.spring.CucumberContextConfiguration;


@CucumberContextConfiguration
//@ContextConfiguration(classes = Application.class,initializers = ConfigFileApplicationContextInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
@ActiveProfiles("integrationTests")
@TestPropertySource(value = "classpath:application-it.properties")
public class ApplicationCucumberContextConfiguration {
    
}
