package com.dbconnect.demodb.Service;

import java.util.List;
import java.util.Map;

import com.dbconnect.demodb.Payloads.AuthSignIn;
import com.dbconnect.demodb.Exception.ResourceNotFoundException;
import com.dbconnect.demodb.Model.PersonModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface PersonServiceInterface {
	public abstract ResponseEntity<?> createPerson(PersonModel person);
	public abstract ResponseEntity<PersonModel> updatePerson(Long id, PersonModel person) throws ResourceNotFoundException;
	public abstract Map<String, Boolean> deletePerson(Long id) throws ResourceNotFoundException;
	public abstract List<PersonModel> getPeople();
	public abstract PersonModel getPersonById(Long id) throws ResourceNotFoundException;
	public abstract ResponseEntity<?> signinService(AuthSignIn authBody) throws JsonProcessingException;
}
