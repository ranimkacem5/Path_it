package com.praject.pathit.dao.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Document( collection = "domaine")
public class Domaine {
    @Id
    
    private String id ; 
  
    private String nom ; 
 
    private String description ;
   
    
    private   String roadmap ; 





    

    
}

