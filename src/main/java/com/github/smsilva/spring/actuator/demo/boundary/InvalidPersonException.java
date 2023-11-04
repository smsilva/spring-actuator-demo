package com.github.smsilva.spring.actuator.demo.boundary;

import com.github.smsilva.spring.actuator.demo.entity.Person;

public class InvalidPersonException extends RuntimeException {

    private final Person person;

    public InvalidPersonException(Person person) {
        super("Invalid Person: " + person);
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

}
