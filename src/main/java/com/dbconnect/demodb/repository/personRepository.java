package com.dbconnect.demodb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbconnect.demodb.model.person;


@Repository
public interface personRepository extends JpaRepository<person, Long>	 {

}
