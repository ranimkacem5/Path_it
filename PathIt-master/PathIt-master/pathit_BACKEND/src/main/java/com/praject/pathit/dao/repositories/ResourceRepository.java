package com.praject.pathit.dao.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.praject.pathit.dao.entities.Resource;

public interface ResourceRepository  extends MongoRepository<Resource, String> {



}
