package com.praject.pathit.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.praject.pathit.dao.entities.Domaine;
import com.praject.pathit.dao.entities.Formation;
import com.praject.pathit.dao.repositories.FormationRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/admin/formation")
@RequiredArgsConstructor // Génère un constructeur pour les final fields
@CrossOrigin(origins = "http://localhost:3000") 
public class FormationController {
    private final FormationRepository formationRepository;
    private static final Logger logger = LoggerFactory.getLogger(FormationController.class);

    @PostMapping("/formation-form")
    public ResponseEntity<String> addFormation(@Valid @RequestBody Formation formation, BindingResult result) {
        if (result.hasErrors()) {
            logger.error("Validation errors: " + result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data.");
        }
        if (formation.getTitle() == null || formation.getTitle().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: 'title' is required.");
        }
        if (formation.getDescription() == null || formation.getDescription().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: 'description' is required.");
        }
        if (formation.getDuration() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: 'duration' must be greater than zero.");
        }
        if (formation.getLevel() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: 'level' is required.");
        }
        if (formation.getLink() == null || formation.getLink().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: 'link' is required.");
        }

        // Si toutes les conditions sont valides, enregistrer la formation
        formationRepository.save(formation);
        logger.info("Formation added successfully with ID: " + formation.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Formation added successfully with ID: " + formation.getId());
    }

    @GetMapping("/formations")
    public ResponseEntity<?> getAllFormations() {
       

         try {
        
            List<Formation> formation = formationRepository.findAll();
            for (Formation f:formation)
            {
                System.err.println(f.getLevel());
            }
        return ResponseEntity.ok(formation); 
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des domaines");
    }
       
    }

    // Récupérer une ressource spécifique par ID
    @GetMapping("/getFormation/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable String id) {
        Optional<Formation> formation = formationRepository.findById(id);
        if (formation.isPresent()) {
            return ResponseEntity.ok(formation.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
    
            }
            @GetMapping("/formation-edit/{id}")
            public ResponseEntity<?> getDomaineById(@PathVariable("id") String id) {
                try {
                    Formation f = formationRepository.findById(id).get();
                    if (f != null) {
                        System.out.println(f.getLink());
                        return ResponseEntity.ok(f);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("formation introuvable");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération de formation");
                }
            }
            
    // Mettre à jour une ressource existante
    @PutMapping("/formation-edit/{id}")
    public ResponseEntity<String> updateFormation(@PathVariable String id, @Valid @RequestBody Formation updatedFormation, BindingResult result) {
        if (result.hasErrors()) {
            logger.error("Validation errors: " + result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data.");
        }

        // Vérifier si la formation existe
        if (formationRepository.existsById(id)) {
            updatedFormation.setId(id); // Garder l'ID de la formation intact
            formationRepository.save(updatedFormation); // Sauvegarder la formation mise à jour
            logger.info("Formation updated successfully with ID: " + id);
            return ResponseEntity.ok("Formation updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Formation with ID " + id + " not found.");
    }

    // Supprimer une ressource par ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFormation(@PathVariable String id) {
        if (formationRepository.existsById(id)) {
            formationRepository.deleteById(id);
            logger.info("Formation deleted successfully with ID: " + id);
            return ResponseEntity.ok("Formation deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Formation with ID " + id + " not found.");
    }
}