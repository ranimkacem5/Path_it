package com.praject.pathit.dao.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "bootcamps")
public class Bootcamp {

    @Id
    private String id; // MongoDB ID (String)

    private String name; 
    private String description;
    private  Date startDate;
    private Date endDate;
    private Resource resource;
   
    private String level; 


   /*@DBRef
    private List<Domaine> domaine;*/

}



