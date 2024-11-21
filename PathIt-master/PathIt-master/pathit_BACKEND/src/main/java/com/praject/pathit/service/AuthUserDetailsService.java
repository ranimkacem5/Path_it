package com.praject.pathit.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.praject.pathit.repository.UserRepository;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class AuthUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<com.praject.pathit.model.User> user= userRepository.findByEmail(email.toLowerCase());
        if(!user.isPresent())
            throw new UsernameNotFoundException(email);
            else{
                return User.builder()
                .username(user.get().getEmail())
                .password(user.get().getPassword())
                .build();
                
            
            }
    }
    

}
