package com.github.smsilva.spring.actuator.demo.config;

import com.github.smsilva.spring.actuator.demo.boundary.InvalidPersonException;
import com.github.smsilva.spring.actuator.demo.entity.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidPersonException.class)
    ProblemDetail handleInvalidPersonException(InvalidPersonException exception) {
        Person person = exception.getPerson();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage());
        problemDetail.setTitle("Invalid Person [custom]");
        problemDetail.setDetail("Person object is invalid");
        problemDetail.setProperty("Category", "Person");
        problemDetail.setProperty("Timestamp", Instant.now());
        problemDetail.setProperty("Problems", getProblems(person));
        return problemDetail;
    }

    private static Map<String, String> getProblems(Person person) {
        Map<String, String> problems = new HashMap<>();

        if (person == null) {
            problems.put("Person", "null");
        } else {
            if (person.getId() == null) {
                problems.put("id", "null");
            }

            if (person.getName() == null) {
                problems.put("name", "null");
            }
        }
        return problems;
    }

}