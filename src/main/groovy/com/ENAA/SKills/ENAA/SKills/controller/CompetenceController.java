package com.ENAA.SKills.ENAA.SKills.controller;

import com.ENAA.SKills.ENAA.SKills.model.Competence;
import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;
import com.ENAA.SKills.ENAA.SKills.service.CompetenceService;
import com.ENAA.SKills.ENAA.SKills.service.SousCompetenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/competences")
public class CompetenceController {
    @Autowired
    private CompetenceService competenceService;
    @Autowired
    private SousCompetenceService sousCompetenceService;

    // DTO pour la création
    public static class CompetenceCreationDto {
        public String nom;
        public List<String> sousCompetences;
    }

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
