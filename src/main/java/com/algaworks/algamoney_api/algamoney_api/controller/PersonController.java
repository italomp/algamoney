package com.algaworks.algamoney_api.algamoney_api.controller;

import com.algaworks.algamoney_api.algamoney_api.events.CreatedResourceEvent;
import com.algaworks.algamoney_api.algamoney_api.model.Address;
import com.algaworks.algamoney_api.algamoney_api.model.Person;
import com.algaworks.algamoney_api.algamoney_api.repository.PersonRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonRepository repository;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    @GetMapping
    public ResponseEntity<List<Person>> getAll() {
        return ResponseEntity.ok().body(this.repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable Long id) {
        try {
            Person person = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Person not found"));
            return ResponseEntity.ok().body(person);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Person> create(@Valid @RequestBody Person person, HttpServletResponse response) {
        Person saved = this.repository.save(person);
        this.eventPublisher.publishEvent(new CreatedResourceEvent(this, response, saved.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        this.repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> patch(@PathVariable Long id, @RequestBody Person person) {
        Person savedPerson = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Person not found"));
        if(person.getName() != null) {
            savedPerson.setName(person.getName());
        }
        if (person.getActive() != null) {
            savedPerson.setActive(person.getActive());
        }
        if (person.getAddress() != null) {
            Address oldAddress = savedPerson.getAddress();
            Address newAddress = person.getAddress();

            if (oldAddress == null) {
                savedPerson.setAddress(newAddress);
            } else {
                if (newAddress.getStreet() != null) {
                    oldAddress.setStreet(newAddress.getStreet());
                }
                if (newAddress.getNumber() != null) {
                    oldAddress.setNumber(newAddress.getNumber());
                }
                if (newAddress.getNeighbor() != null) {
                    oldAddress.setNeighbor(newAddress.getNeighbor());
                }
                if (newAddress.getCity() != null) {
                    oldAddress.setCity(newAddress.getCity());
                }
                if (newAddress.getState() != null) {
                    oldAddress.setState(newAddress.getState());
                }
                if (newAddress.getCep() != null) {
                    oldAddress.setCep(newAddress.getCep());
                }
            }
        }
        this.repository.save(savedPerson);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
