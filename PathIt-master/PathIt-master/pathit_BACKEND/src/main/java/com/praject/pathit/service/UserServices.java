package com.praject.pathit.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.praject.pathit.model.User;
import com.praject.pathit.repository.UserRepository;

public class UserServices {
 @Autowired
    private UserRepository repo;
    public void saveUpdate(User user) {
       repo.save(user);
    }

}
