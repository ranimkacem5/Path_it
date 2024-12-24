package com.praject.pathit.dao.entities;


import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "resources")
public class Resource {

    @Id
    private String id; 

    private String link; 
    private String type; 

   /*  @DBRef
    private List<Comment> comments; // Référence aux commentaires*/

  
}

