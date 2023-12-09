package com.tux;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
//@Configuration
//@ComponentScan(basePackages = {"com.tux"})
//@EnableAutoConfiguration
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    public record Person(String name, int age) {}
    public record MyNotesResponse(
            String title,
            Person owner,
            List<String> Notes) {}

    @GetMapping("/")
    public MyNotesResponse greet() {
        return new MyNotesResponse(
                "Hello",
                new Person("Tux", 21),
                List.of("Note 1", "Note 2")
        );
    }

}
