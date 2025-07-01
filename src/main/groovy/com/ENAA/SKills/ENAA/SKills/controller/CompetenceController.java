package com.ENAA.SKills.ENAA.SKills.controller;

import com.ENAA.SKills.ENAA.SKills.model.Competence;
import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;
import com.ENAA.SKills.ENAA.SKills.service.CompetenceService;
import com.ENAA.SKills.ENAA.SKills.service.SousCompetenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/competences")
public class CompetenceController {
    @Autowired
    private CompetenceService competenceService;
    @Autowired
    private SousCompetenceService sousCompetenceService;

    // DTO pour la création groupée
    public static class CompetenceCreationDto {
        public String nom;
        public List<String> sousCompetences;
    }

    // CRUD de base
    @PostMapping
    public Competence create(@RequestBody Competence competence) {
        return competenceService.save(competence);
    }

    @GetMapping
    public List<Competence> getAll() {
        return competenceService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Competence> getById(@PathVariable Long id) {
        return competenceService.findById(id);
    }

    @PutMapping("/{id}")
    public Competence update(@PathVariable Long id, @RequestBody Competence competence) {
        competence.setId(id);
        return competenceService.save(competence);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        competenceService.deleteById(id);
    }

    // Création d'une compétence avec sous-compétences
    @PostMapping("/create-with-sous-competences")
    public Competence createCompetenceWithSousCompetences(@RequestBody CompetenceCreationDto dto) {
        Competence competence = new Competence();
        competence.setNom(dto.nom);
        competence = competenceService.save(competence);
        if (dto.sousCompetences != null) {
            for (String titre : dto.sousCompetences) {
                SousCompetence sc = new SousCompetence();
                sc.setTitre(titre);
                sousCompetenceService.save(sc);
            }
        }
        return competence;
    }
}
