package com.dbconnect.demodb.Controller;

import com.dbconnect.demodb.Payloads.AuthSignIn;
import com.dbconnect.demodb.Exception.ResourceNotFoundException;
import com.dbconnect.demodb.Model.PersonModel;
import com.dbconnect.demodb.Payloads.TokenInvalid;
import com.dbconnect.demodb.Payloads.successResponse;
import com.dbconnect.demodb.Service.PersonService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class PersonController {

	@Autowired
	private PersonService personService;

	private static com.dbconnect.demodb.Payloads.signInResponse signInResponse = new com.dbconnect.demodb.Payloads.signInResponse();


	// get person
	@GetMapping("person")
	public ResponseEntity<?> getPeopleAuth() throws IOException {
		System.out.println("getpeople called");
		HttpServletRequest curr_request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		HttpServletResponse res = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
		Object token = curr_request.getAttribute("token");
		if(token == null){
			TokenInvalid err = new TokenInvalid("Invalid Token");
			return ResponseEntity.internalServerError().body(err);
		}
		List<PersonModel> allPeople =  this.personService.getPeople();
		return ResponseEntity.ok().body(allPeople);
	}

	// get person by id
	@GetMapping("person/{id}")
	public ResponseEntity<PersonModel> getPersonbyId(@PathVariable(value = "id") Long personId) throws ResourceNotFoundException {
		PersonModel person = personService.getPersonById(personId);
		return ResponseEntity.ok().body(person);
	}
	
	
	// save person
	@PostMapping("person")
	public ResponseEntity<?> createPerson(@RequestBody @Validated PersonModel person) {
		return personService.createPerson(person);
	}
	
	
	
	// update person
	@PutMapping("person/{id}")
	public ResponseEntity<PersonModel> updatePerson(@PathVariable(value = "id") Long personId,
													@Validated @RequestBody PersonModel personDetails) throws ResourceNotFoundException {
		return personService.updatePerson(personId, personDetails);
	}
	
	
	// delete person
	@DeleteMapping("person/{id}")
	public Map<String, Boolean> deletePerson(@PathVariable(value = "id") Long personId) throws ResourceNotFoundException {
		return personService.deletePerson(personId);
	}

	// sign in
	@PostMapping("person/signin")
	public ResponseEntity<?> signIn(@RequestBody AuthSignIn authSignIn){
		try {
			ResponseEntity<?> resp = personService.signinService(authSignIn);
			if(resp.getStatusCode() == HttpStatus.OK) {
				String resBody = (String) resp.getBody();
				ObjectMapper mapper = new ObjectMapper();
				JsonNode resJson = mapper.readTree(resBody);

				JsonNode user = resJson.get("user");
				signInResponse.setUsername(user.get("username").toString());
				signInResponse.setId(user.get("id").asLong());
				signInResponse.setAccessToken(resJson.get("accessToken").textValue());
				signInResponse.setName(user.get("name").toString());
				System.out.println(signInResponse.getAccessToken());
			}
			else {
				System.out.println("moonjz");
			}


			return resp;
		} catch (Exception e) {
			System.out.println("Error");
			return ResponseEntity.internalServerError().body("Some Error");
		}

	}
	
}
