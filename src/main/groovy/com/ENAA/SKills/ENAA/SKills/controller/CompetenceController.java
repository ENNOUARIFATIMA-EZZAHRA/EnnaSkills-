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

    // DTO pour la mise à jour
    public static class CompetenceUpdateDto {
        public String nom;
    }


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
    public Competence update(@PathVariable Long id, @RequestBody CompetenceUpdateDto dto) {
        Competence competence = competenceService.findById(id).orElseThrow();
        if (dto.nom != null) competence.setNom(dto.nom);
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
                sc.setCompetence(competence); // الربط هنا ضروري
                sousCompetenceService.save(sc);
            }
        }
        return competence;
    }

    @GetMapping("/{competenceId}/acquired-by/{apprenantId}")
    public boolean isCompetenceAcquiredByApprenant(@PathVariable Long competenceId, @PathVariable Long apprenantId) {
        return competenceService.isCompetenceAcquiredByApprenant(competenceId, apprenantId);
    }
}
