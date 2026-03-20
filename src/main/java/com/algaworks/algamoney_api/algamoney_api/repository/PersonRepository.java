package com.algaworks.algamoney_api.algamoney_api.repository;

import com.algaworks.algamoney_api.algamoney_api.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
