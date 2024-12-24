package com.praject.pathit.business.services;

import java.util.List;

import com.praject.pathit.dao.entities.Formation;


public interface FormationService  {
    List<Formation> getFormations();
    Formation getFormationById(String id);
    
    //create
    Formation addFormation(Formation formation);
    //Update
    Formation updateFormation(Formation formation);
    //Delete
    void deleteFormationById(String id);

}
