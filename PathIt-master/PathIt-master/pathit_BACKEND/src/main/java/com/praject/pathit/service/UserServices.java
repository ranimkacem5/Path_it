package com.praject.pathit.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.praject.pathit.dao.repositories.UserRepository;
import com.praject.pathit.web.model.User;

public class UserServices {
 @Autowired
    private UserRepository repo;
    public void saveUpdate(User user) {
       repo.save(user);
    }

}
