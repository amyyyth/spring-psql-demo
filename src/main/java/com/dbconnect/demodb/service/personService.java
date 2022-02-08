package com.dbconnect.demodb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dbconnect.demodb.model.person;
import com.dbconnect.demodb.repository.personRepository;

@Service
public class personService implements personServiceInterface {

	private personRepository personRepository;

	@Override
	public person createPerson(person person) {
		return personRepository.save(person);
	}

	@Override
	public void updatePerson(Long id, person person) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePerson(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<person> getPeople() {
		return this.personRepository.findAll();
	}

	@Override
	public Optional<person> getPersonById(Long id) {
		return personRepository.findById(id);
	}

	
	
	
	
}
