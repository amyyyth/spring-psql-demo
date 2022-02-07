package com.dbconnect.demodb.service;

import java.util.List;

import com.dbconnect.demodb.model.person;

public interface personServiceInterface {
	public abstract person createPerson(person person);
	public abstract void updatePerson(String id, person person);
	public abstract void deletePerson(String id);
	public abstract List<person> getPeople();
	public abstract person getPersonById();
}
