package com.praject.pathit.business.services;

import java.util.List;

import com.praject.pathit.dao.entities.Comment;

public interface CommentService {
     List<Comment> getComments();
     Comment getCommentById(String id);
    
    //create
    Comment addComment(Comment comment);
    //Update
    Comment updateComment(Comment comment);
    //Delete
    void deleteCommentById(String id);
    
     

}
