package com.dbconnect.demodb.service;

import java.util.List;
import java.util.Optional;

import com.dbconnect.demodb.model.person;

public interface personServiceInterface {
	public abstract person createPerson(person person);
	public abstract void updatePerson(Long id, person person);
	public abstract void deletePerson(Long id);
	public abstract List<person> getPeople();
	public abstract Optional<person> getPersonById(Long id);
}
