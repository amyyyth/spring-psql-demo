package com.dbconnect.demodb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbconnect.demodb.exception.EmailExistsException;
import com.dbconnect.demodb.exception.ResourceNotFoundException;
import com.dbconnect.demodb.model.person;
import com.dbconnect.demodb.repository.personRepository;
import com.dbconnect.demodb.service.personService;

@RestController
@RequestMapping("/api/v1/")
public class personController {
	
	@Autowired
	private personRepository personRepository;
	
	@Autowired
	private personService personService;
	
	// get person
	@GetMapping("person")
	public List<person> getPeople(){
		return this.personRepository.findAll();
	}
	
	// get person by id
	@GetMapping("person/{id}")
	public ResponseEntity<person> getPersonbyId(@PathVariable(value = "id") Long personId)
		throws ResourceNotFoundException {
			person person = personRepository.findById(personId)
					.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id: "+personId));
			return ResponseEntity.ok().body(person);
	}
	
	
	// save person
	@PostMapping("person")
	public ResponseEntity<?> createPerson(@RequestBody @Validated person person) {
		try {
			//person resp = personService.createPerson(person);
			person resp = personService.createPerson(person);
			return ResponseEntity.ok().body(resp);
		} catch(Exception e) {
			Throwable t = e.getCause();
		    while ((t != null) && !(t instanceof ConstraintViolationException)) {
		        t = t.getCause();
		    }
		    if (t instanceof ConstraintViolationException) {
		    	return ResponseEntity.internalServerError()
		    			.body(new EmailExistsException("Account already exists"));
		    }
		    else {
		    	return ResponseEntity.internalServerError()
		    			.body("Some weird error");
		    }
		    
			
		}
	}
	
	
	
	// update person
	@PutMapping("person/{id}")
	public ResponseEntity<person> updatePerson(@PathVariable(value = "id") Long personId,
			@Validated @RequestBody person personDetails) throws ResourceNotFoundException {
		person person = personRepository.findById(personId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id: "+personId));
		
		if(personDetails.getEmail()!= null) {
			person.setEmail(personDetails.getEmail());
		}
		if(personDetails.getName()!= null) {
			person.setName(personDetails.getName());
		}
		if(personDetails.getAge()!=0) {
			person.setAge(personDetails.getAge());
		}
				
		return ResponseEntity.ok(this.personRepository.save(person));
	}
	
	
	// delete person
	@DeleteMapping("person/{id}")
	public Map<String, Boolean> deletePerson(@PathVariable(value = "id") Long personId) throws ResourceNotFoundException {
		person person = personRepository.findById(personId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id: "+personId));
		
		this.personRepository.delete(person);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
	}
	
}
