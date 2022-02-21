package com.dbconnect.demodb.aop;


import com.dbconnect.demodb.payloads.TokenInvalid;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class PersonAuth {
    @Around("userSessionValidationFromURLTokenPointcut()")
    public Object getAuth(ProceedingJoinPoint pjp) throws Throwable {
        RestTemplate restTemplate = new RestTemplate();
        String base_uri = "https://ums.bytetale.com/umservices/api/auth/validate";
//        String token = String.valueOf(personController.getAuthToken());
        HttpServletRequest curr_request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String token = curr_request.getHeader("Authorization");
        if(token == null){
            System.out.println("no token");
            return ResponseEntity.internalServerError().body(new TokenInvalid("No token provided"));
        }
        System.out.println(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", token);
        HttpEntity<String> request =
                new HttpEntity<String>("", headers);
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


    @Pointcut("execution(* com.dbconnect.demodb.controller.PersonController.*Auth(..))")
    public void userSessionValidationFromURLTokenPointcut() {
        System.out.println("This method is VerifyToken.");
    }




}
