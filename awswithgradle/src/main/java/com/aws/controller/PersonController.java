package com.aws.controller;

import com.aws.dto.PersonDto;
import com.aws.message.DataProducer;
import com.aws.model.PersonEntity;
import com.aws.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController
{
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DataProducer dataProducer;

    @RequestMapping(value = "hello")
    public String hello()
    {
        return "hello";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addPerson(@RequestBody PersonDto personDto)
    {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(personDto.getName());
        personEntity.setSurname(personDto.getSurname());

        PersonEntity savedPerson = personRepository.save(personEntity);

        dataProducer.sendData(savedPerson.getId(), savedPerson.getName(), savedPerson.getSurname());

        return personEntity.toString();

    }

    @RequestMapping(value = "/{name}/{surname}", method = RequestMethod.GET)
    public String addPerson(@PathVariable String name, @PathVariable String surname)
    {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(name);
        personEntity.setSurname(surname);

        PersonEntity savedPerson = personRepository.save(personEntity);

        dataProducer.sendData(savedPerson.getId(), savedPerson.getName(), savedPerson.getSurname());

        return personEntity.toString();
    }
}
