package com.dbconnect.demodb.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbconnect.demodb.Payloads.AuthSignIn;
import com.dbconnect.demodb.Exception.EmailExistsException;
import com.dbconnect.demodb.Exception.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import com.dbconnect.demodb.Model.PersonModel;
import com.dbconnect.demodb.Repository.PersonRepository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class PersonService implements PersonServiceInterface {

	@Autowired
	private PersonRepository personRepository;

	@Override
	public ResponseEntity<?> createPerson(PersonModel person) {

		try {
			PersonModel resp = personRepository.save(person);
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
				e.printStackTrace();
				return ResponseEntity.internalServerError()
						.body("Some weird error");
			}


		}
	}

	@Override
	public ResponseEntity<PersonModel> updatePerson(Long personId, PersonModel personDetails) throws ResourceNotFoundException {
		// TODO Auto-generated method stub

		PersonModel person = personRepository.findById(personId)
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

	@Override
	public Map<String, Boolean> deletePerson(Long personId) throws ResourceNotFoundException {
		PersonModel person = personRepository.findById(personId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id: "+personId));

		this.personRepository.delete(person);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return response;
		
	}

	@Override
	public List<PersonModel> getPeople() {
		return this.personRepository.findAll();
	}

	@Override
	public PersonModel getPersonById(Long personId) throws ResourceNotFoundException{
		PersonModel person = personRepository.findById(personId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id: "+personId));
		return person;
	}

	@Override
	public ResponseEntity<?> signinService(AuthSignIn authBody) throws HttpStatusCodeException {
		RestTemplate restTemplate = new RestTemplate();
		String base_uri = "https://ums.bytetale.com/umservices/api/auth/signin";
		String token = "";
		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		headers.set("Authorization", "Bearer "+token);
		HttpEntity<AuthSignIn> request =
				new HttpEntity<AuthSignIn>(authBody, headers);
		ResponseEntity<?> res = restTemplate.exchange(
				base_uri, HttpMethod.POST, request, String.class);

		return res;
	}


}
