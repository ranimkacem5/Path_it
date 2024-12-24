package com.praject.pathit.dao.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.praject.pathit.dao.entities.Bootcamp;


public interface BootcampRepository extends MongoRepository<Bootcamp, String> {

}