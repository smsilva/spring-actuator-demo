package com.github.smsilva.spring.actuator.demo;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/person")
@Timed("person")
public class PersonController {

    @Autowired
    PersonMemoryRepository repository;

    final MeterRegistry meterRegistry;

    public Supplier<Number> fetchPersonCount() {
        return ()-> repository.findAll().size();
    }

    public PersonController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        Gauge.builder("person_total", fetchPersonCount()).
                description("The Person count in the Person Memory Repository").
                register(meterRegistry);
    }

    @PostMapping
    @Timed(value = "person_create", description = "a number of POST requests to /person/create endpoint", percentiles={0.5,0.9})
    ResponseEntity<?> create(@NonNull Person person) {
        repository.save(person);

        return ResponseEntity
                .ok(person);
    }

    @GetMapping
    @Timed(value = "person_list_all", description = "time to retrieve all person list", percentiles={0.5,0.9})
    ResponseEntity<?> listAll() {
        List<Person> list = repository.findAll();

        return ResponseEntity
                .ok(list);
    }

}
