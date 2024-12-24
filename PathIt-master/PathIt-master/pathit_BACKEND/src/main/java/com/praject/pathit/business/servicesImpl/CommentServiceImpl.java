package com.praject.pathit.business.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.praject.pathit.business.services.CommentService;
import com.praject.pathit.dao.entities.Comment;
import com.praject.pathit.dao.repositories.CommentRepository;

public class CommentServiceImpl implements CommentService {
    @Autowired 
    private final CommentRepository commentRepository;
   /* @Autowired
    private PhotoRepository photoRepository;*/
    public CommentServiceImpl(CommentRepository commentRepository){
     this.commentRepository=commentRepository;
   
     

    }
    @Override
    public List<Comment> getComments() {
        return this.commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(String id) {
        return this.commentRepository.findById(id).get();
    }

    @Override
    public Comment addComment(Comment comment) {
        if(comment==null){
            return null;
        }
       return this.commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        if(comment==null){
            return null;
        }
       return this.commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(String id) {
        if(id==null){
            return ;
        }
         this.commentRepository.deleteById(id);
    }

}
