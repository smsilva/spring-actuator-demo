package com.github.smsilva.spring.actuator.demo.boundary;

import com.github.smsilva.spring.actuator.demo.entity.Person;
import com.github.smsilva.spring.actuator.demo.control.PersonMemoryRepository;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/person")
@Timed("person")
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    PersonMemoryRepository repository;

    final MeterRegistry meterRegistry;

    public Supplier<Number> fetchPersonCount() {
        return ()-> repository.findAll().size();
    }

    public PersonController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        Gauge.builder("person_records_max", fetchPersonCount()).
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
        LOGGER.info("Listing all persons");
        
        List<Person> list = repository.findAll();

        return ResponseEntity
                .ok(list);
    }

    @DeleteMapping("/{id}")
    @Timed(value = "person_delete", description = "time to delete a person", percentiles={0.5,0.9})
    ResponseEntity<?> delete(@PathVariable Long id) {
        LOGGER.info("Deleting person with id {}", id);

        if (id == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        repository.remove(id);

        return ResponseEntity
                .noContent()
                .build();
    }

}
