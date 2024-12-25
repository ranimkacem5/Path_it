package com.praject.pathit.dao.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.praject.pathit.dao.entities.Domaine;

@Repository
public interface DomaineRepository extends MongoRepository<Domaine, String> {
  
}
