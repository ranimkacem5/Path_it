package com.praject.pathit.business.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praject.pathit.business.services.DomaineService;
import com.praject.pathit.dao.entities.Domaine;
import com.praject.pathit.dao.repositories.DomaineRepository;


@Service
public class DomaineservicesImpl implements DomaineService {



    private final DomaineRepository domaineRepository;

@Autowired
public DomaineservicesImpl(DomaineRepository domaineRepository) {
    this.domaineRepository = domaineRepository;
    System.out.println("DomaineRepository bean initialized: " + (domaineRepository != null));
}

    @Override
    public void saveDomaine(Domaine domaine) {
        domaineRepository.save(domaine);
    }

    @Override
    public Domaine updateDomaine(Domaine domaine) {
        return domaineRepository.save(domaine);
    }

    @Override
    public void delete(String id) {
        domaineRepository.deleteById(id);
    }

    @Override
    public Domaine getDomaineById(String id) {
        return domaineRepository.findById(id).orElse(null);
    }

    @Override
    public List<Domaine> getAllDomaines() {
        return domaineRepository.findAll();
    }
    
}
