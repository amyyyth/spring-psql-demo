package com.dbconnect.demodb.aop;


import com.dbconnect.demodb.payloads.TokenInvalid;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
@Component
public class PersonAuth {

    static Logger log = Logger.getLogger(PersonAuth.class.getName());

    @Value("${backend.validate.uri}")
    private String base_uri;

    @Around("userSessionValidationFromURLTokenPointcut()")
    public Object getAuth(ProceedingJoinPoint pjp) throws Throwable {
        RestTemplate restTemplate = new RestTemplate();
        HttpServletRequest curr_request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String token = curr_request.getHeader("Authorization");
        if(token == null){
            System.out.println("no token");
            return ResponseEntity.internalServerError().body(new TokenInvalid("No token provided"));
        }
//        System.out.println(token);
        log.log(Level.ALL,token);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", token);
        HttpEntity<String> request =
                new HttpEntity<String>("", headers);

        try {
            String resBody = restTemplate.exchange(
                    base_uri, HttpMethod.POST, request, String.class).getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode resJson = mapper.readTree(resBody);
            String isSuccess = resJson.get("success").toString();
            if ("true".equals(isSuccess)){
                System.out.println("auth success");
                curr_request.setAttribute("token",token);
                return pjp.proceed();
            }
            else{
                curr_request.removeAttribute("token");
                System.out.println("Auth fail");
                return ResponseEntity.internalServerError().body(new TokenInvalid("Invalid Token"));
            }
        }
        catch (RestClientResponseException e){
            return ResponseEntity.internalServerError().body("Some issue with the UMS server");
        }

    }


    @Pointcut("execution(* com.dbconnect.demodb.controller.PersonController.*Auth(..))")
    public void userSessionValidationFromURLTokenPointcut() {
        System.out.println("This method is VerifyToken.");
    }




}
