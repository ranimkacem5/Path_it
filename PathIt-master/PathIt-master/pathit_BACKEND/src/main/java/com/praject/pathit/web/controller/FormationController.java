package com.praject.pathit.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.praject.pathit.dao.entities.Formation;

import com.praject.pathit.dao.repositories.FormationRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/formation")
@RequiredArgsConstructor // Génère un constructeur pour les final fields
@CrossOrigin(origins = "http://localhost:3000") 
public class FormationController  {
    private final FormationRepository formationRepository;
    @PostMapping("/add")
    public ResponseEntity<String> addFormation(@Valid @RequestBody Formation formation,  BindingResult result) {
        if (result.hasErrors()) {
            // Si des erreurs de validation sont trouvées
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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Formation added successfully with ID: " + formation.getId());
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Formation>> getAllFormations() {
        List<Formation> formation = formationRepository.findAll();
    
        if (formation.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
    
        return ResponseEntity.ok(formation);
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

    // Mettre à jour une ressource existante
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateFormation(@PathVariable String id, @RequestBody Formation updatedFormation) {
        // Vérifier si la formation existe
        if (formationRepository.existsById(id)) {
            updatedFormation.setId(id); // Garder l'ID de la formation intact
            formationRepository.save(updatedFormation); // Sauvegarder la formation mise à jour
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
            return ResponseEntity.ok("Formation deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Formation with ID " + id + " not found.");
    }
    


}
