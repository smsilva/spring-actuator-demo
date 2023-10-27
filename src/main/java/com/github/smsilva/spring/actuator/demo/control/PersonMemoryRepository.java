package com.github.smsilva.spring.actuator.demo.control;

import com.github.smsilva.spring.actuator.demo.entity.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PersonMemoryRepository {

    private final Map<Long, Person> people = new ConcurrentHashMap<>();

    public PersonMemoryRepository() {
        people.put(1L, new Person(1L, "Silvio Silva"));
        people.put(2L, new Person(2L, "Patrícia Alves"));
        people.put(3L, new Person(3L, "Larissa Silva"));
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

    public void remove(Long id) {
        people.remove(id);
    }

}
