package com.dbconnect.demodb.payloads;

import com.dbconnect.demodb.model.PersonModel;

import java.util.Date;
import java.util.List;


public class successResponse {

    private String authorized;
    private List<PersonModel> people;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthorized() {
        return authorized;
    }

    public void setAuthorized(String authorized) {
        this.authorized = authorized;
    }

    public List<PersonModel> getPeople() {
        return people;
    }

    public void setPeople(List<PersonModel> people) {
        this.people = people;
    }

    public successResponse(String authorized, List<PersonModel> people) {
        this.authorized = authorized;
        this.people = people;
        this.date = new Date();
    }

    public successResponse(String authorized, List<PersonModel> people, Date date) {
        this.authorized = authorized;
        this.people = people;
        this.date = date;
    }
}
