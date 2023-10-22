package com.github.smsilva.spring.actuator.demo;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
@Timed("person")
public class PersonController {

    @Autowired
    PersonMemoryRepository repository;

    @PostMapping
    @Timed(value = "person.create", longTask = true)
    ResponseEntity<?> create(@NonNull Person person) {
        repository.save(person);
        return ResponseEntity
                .ok(person);
    }

    @GetMapping
    @Timed(value = "person.all", longTask = true)
    ResponseEntity<?> listAll() {
        List<Person> list = repository.findAll();
        return ResponseEntity
                .ok(list);
    }

}
