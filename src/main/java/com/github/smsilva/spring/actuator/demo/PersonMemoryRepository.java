package com.github.smsilva.spring.actuator.demo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PersonMemoryRepository {

    private final Map<Long, Person> people = new ConcurrentHashMap<>();

    public PersonMemoryRepository() {
    }

    public Person save(Person person) {
        people.put(person.getId(), person);
        return person;
    }

    public Person findById(Long id) {
        return people.get(id);
    }

    public Person deleteById(Long id) {
        return people.remove(id);
    }

    public List<Person> findAll() {
        return new ArrayList<>(people.values());
    }

}
