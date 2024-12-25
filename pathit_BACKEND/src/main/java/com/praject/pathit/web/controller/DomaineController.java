package com.praject.pathit.web.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.praject.pathit.business.services.DomaineService;
import com.praject.pathit.dao.entities.Domaine;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/admin/domaines")
@RequiredArgsConstructor // Lombok génère un constructeur pour les champs final
@CrossOrigin(origins = "http://localhost:3000")
public class DomaineController {

    private final DomaineService domaineService; // Injecté automatiquement par Spring

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images";
void DomaineController( DomaineService domaineService)
{
  domaineService=domaineService;
}
    @PostMapping("/domaine-form")
    public ResponseEntity<String> createDomaine(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("roadmap") MultipartFile roadmap) {

        System.out.println("Nom: " + nom + ", Description: " + description + ", Fichier: " + roadmap.getOriginalFilename());

        try {
            // Vérification du fichier roadmap
            if (roadmap.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La roadmap est obligatoire");
            }

            // Création du répertoire si nécessaire
            Path directoryPath = Paths.get(uploadDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            
            String fileName = roadmap.getOriginalFilename();
            Path newFilePath = directoryPath.resolve(fileName);
            System.out.println("image  avant enregistrer ");

            Files.write(newFilePath, roadmap.getBytes());
            System.out.println("image enregistrer ");

            
            Domaine domaine = new Domaine(null, nom, description, fileName);
            domaineService.saveDomaine(domaine);
            System.out.println("domaine enregistrer avec succes ");

            return ResponseEntity.ok("Domaine créé avec succès!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création du domaine");
        }
    }

    @GetMapping("/domaines")
    public ResponseEntity<?> getAllDomaines() {
        try {
            return ResponseEntity.ok(domaineService.getAllDomaines());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des domaines");
        }
    }
     @GetMapping("/domaines/{id}")
    public ResponseEntity<?> getDomaineById(@PathVariable("id") String id) {
        try {
            Domaine domaine = domaineService.getDomaineById(id);
            if (domaine != null) {
                return ResponseEntity.ok(domaine);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Domaine introuvable");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération du domaine");
        }
    }

    @PutMapping("/domaines/{id}")
    public ResponseEntity<String> updateDomaine(
            @PathVariable("id") String id,
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam(value = "roadmap", required = false) MultipartFile roadmap) {

        try {
            // Retrieve the existing Domaine
            Domaine existingDomaine = domaineService.getDomaineById(id);
            if (existingDomaine == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Domaine introuvable");
            }

            // Update the fields
            existingDomaine.setNom(nom);
            existingDomaine.setDescription(description);

            // If a new roadmap file is provided, update it
            if (roadmap != null && !roadmap.isEmpty()) {
                Path directoryPath = Paths.get(uploadDirectory);
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }

                String fileName = roadmap.getOriginalFilename();
                Path newFilePath = directoryPath.resolve(fileName);
                Files.write(newFilePath, roadmap.getBytes());
                existingDomaine.setRoadmap(fileName);
            }

            // Save the updated Domaine
            domaineService.saveDomaine(existingDomaine);

            return ResponseEntity.ok("Domaine mis à jour avec succès!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du domaine");
        }
    }
}
