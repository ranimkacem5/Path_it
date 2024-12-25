package com.praject.pathit.business.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.praject.pathit.dao.entities.Domaine;



@Service
public interface DomaineService {
    void saveDomaine(Domaine domaine);

    Domaine updateDomaine(Domaine domaine);

    void delete(String id);

    Domaine getDomaineById(String id);

    List<Domaine> getAllDomaines();

}