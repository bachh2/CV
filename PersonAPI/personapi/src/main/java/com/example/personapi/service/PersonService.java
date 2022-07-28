package com.example.personapi.service;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.personapi.entity.Person;

import java.util.List;

@Repository
public interface PersonService {

    List<Person> getListPersons();

    Person getPersonById(int id);

    void updatePerson(Person person, int id);

    void registerPerson(Person person);

    void deleteById(int id);

    List<Person> searchByName(@Param("name") String name);
}