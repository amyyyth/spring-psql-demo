package com.dbconnect.demodb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbconnect.demodb.Model.PersonModel;


@Repository
public interface PersonRepository extends JpaRepository<PersonModel, Long>	 {

}
