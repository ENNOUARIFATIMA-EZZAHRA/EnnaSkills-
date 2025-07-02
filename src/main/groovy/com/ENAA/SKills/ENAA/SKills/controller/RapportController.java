package com.ENAA.SKills.ENAA.SKills.controller;

import com.ENAA.SKills.ENAA.SKills.dto.ProgressionDto;
import com.ENAA.SKills.ENAA.SKills.model.Apprenant;
import com.ENAA.SKills.ENAA.SKills.model.Competence;
import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;
import com.ENAA.SKills.ENAA.SKills.model.ValidationSousCompetence;
import com.ENAA.SKills.ENAA.SKills.model.StatutValidation;
import com.ENAA.SKills.ENAA.SKills.service.ApprenantService;
import com.ENAA.SKills.ENAA.SKills.service.CompetenceService;
import com.ENAA.SKills.ENAA.SKills.service.ValidationSousCompetenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rapport")
public class RapportController {
    @Autowired
    private ApprenantService apprenantService;
    @Autowired
    private CompetenceService competenceService;
    @Autowired
    private ValidationSousCompetenceService validationService;

    @GetMapping("/progression/{apprenantId}")
    public ProgressionDto getProgression(@PathVariable Long apprenantId) {
        Optional<Apprenant> apprenantOpt = apprenantService.findById(apprenantId);
        if (apprenantOpt.isEmpty()) return null;
        Apprenant apprenant = apprenantOpt.get();
        ProgressionDto dto = new ProgressionDto();
        dto.apprenantId = apprenant.getId();
        dto.apprenantNom = apprenant.getNom();
        List<Competence> competences = competenceService.findAll();
        List<ValidationSousCompetence> validations = validationService.getAll();
        dto.competences = new ArrayList<>();
        for (Competence c : competences) {
            ProgressionDto.CompetenceProgression cp = new ProgressionDto.CompetenceProgression();
            cp.id = c.getId();
            cp.nom = c.getNom();
            cp.sousCompetences = new ArrayList<>();
            boolean allValid = true;
            if (c.getSousCompetences() != null) {
                for (SousCompetence sc : c.getSousCompetences()) {
                    ProgressionDto.SousCompetenceProgression scp = new ProgressionDto.SousCompetenceProgression();
                    scp.id = sc.getId();
                    scp.titre = sc.getTitre();
                    ValidationSousCompetence v = validations.stream()
                        .filter(val -> val.getApprenant().getId().equals(apprenantId) && val.getSousCompetence().getId().equals(sc.getId()))
                        .findFirst().orElse(null);
                    if (v != null) {
                        scp.statut = v.getStatut().name();
                        if (v.getStatut() != StatutValidation.VALIDE) allValid = false;
                    } else {
                        scp.statut = StatutValidation.NON_VALIDE.name();
                        allValid = false;
                    }
                    cp.sousCompetences.add(scp);
                }
            }
            cp.acquise = allValid && cp.sousCompetences.size() > 0;
            dto.competences.add(cp);
        }
        return dto;
    }
}
