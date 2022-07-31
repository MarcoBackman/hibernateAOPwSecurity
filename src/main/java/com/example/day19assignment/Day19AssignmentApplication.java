package com.example.day19assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class Day19AssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(Day19AssignmentApplication.class, args);
    }

}
