package be.kdg.ip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(entityManagerFactoryRef="emf")
public class IP2Application {

    public static void main(String[] args) {
        SpringApplication.run(IP2Application.class, args);
    }
}
