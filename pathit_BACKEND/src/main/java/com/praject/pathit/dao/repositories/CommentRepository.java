package com.praject.pathit.dao.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.praject.pathit.dao.entities.Comment;

public interface CommentRepository extends MongoRepository<Comment, String>{

}
