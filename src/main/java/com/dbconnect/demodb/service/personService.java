package com.dbconnect.demodb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dbconnect.demodb.model.person;
import com.dbconnect.demodb.repository.personRepository;

@Service
public class personService implements personServiceInterface {

	private personRepository personRepository;
	
	@Override
	public person createPerson(person person) {
		return this.personRepository.save(person);
	}

	@Override
	public void updatePerson(String id, person person) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePerson(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<person> getPeople() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public person getPersonById() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
