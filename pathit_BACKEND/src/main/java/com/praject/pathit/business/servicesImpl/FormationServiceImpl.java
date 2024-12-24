package com.praject.pathit.business.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.praject.pathit.business.services.FormationService;
import com.praject.pathit.dao.entities.Formation;

import com.praject.pathit.dao.repositories.FormationRepository;


public class FormationServiceImpl implements FormationService {
      @Autowired 
    private final FormationRepository formationRepository;
    public FormationServiceImpl(FormationRepository formationRepository)
    {
        this.formationRepository=formationRepository;
    }

    @Override
    public List<Formation> getFormations() {
       return this.formationRepository.findAll();
    }

    @Override
    public Formation getFormationById(String id) {
      return this.formationRepository.findById(id).get();
    }

    @Override
    public Formation addFormation(Formation formation) {
        if(formation==null){
            return null;
        }
        return this.formationRepository.save(formation);
    }

    @Override
    public Formation updateFormation(Formation formation) {
        if(formation==null){
            return null;
        }
        return this.formationRepository.save(formation);
    }

    @Override
    public void deleteFormationById(String id) {
        if(id==null){
            return ;
        }
         this.formationRepository.deleteById(id);
    }
  

}
