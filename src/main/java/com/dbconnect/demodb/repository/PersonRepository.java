package com.dbconnect.demodb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbconnect.demodb.model.PersonModel;


@Repository
public interface PersonRepository extends JpaRepository<PersonModel, Long>	 {

}
