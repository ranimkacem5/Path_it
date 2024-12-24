package com.praject.pathit.dao.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.praject.pathit.dao.entities.Formation;


public interface FormationRepository extends MongoRepository<Formation, String>{

}
