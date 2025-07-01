package com.ENAA.SKills.ENAA.SKills.controller;

import com.ENAA.SKills.ENAA.SKills.model.StatutValidation;
import com.ENAA.SKills.ENAA.SKills.model.ValidationSousCompetence;
import com.ENAA.SKills.ENAA.SKills.model.Apprenant;
import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;
import com.ENAA.SKills.ENAA.SKills.service.ValidationSousCompetenceService;
import com.ENAA.SKills.ENAA.SKills.repository.ApprenantRepository;
import com.ENAA.SKills.ENAA.SKills.repository.SousCompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/validation-sous-competence")
public class ValidationSousCompetenceController {
    @Autowired
    private ValidationSousCompetenceService validationService;
    @Autowired
    private ApprenantRepository apprenantRepository;
    @Autowired
    private SousCompetenceRepository sousCompetenceRepository;

    @PostMapping("/valider")
    public ValidationSousCompetence validerSousCompetence(@RequestParam Long apprenantId, @RequestParam Long sousCompetenceId, @RequestParam StatutValidation statut) {
        Optional<Apprenant> apprenantOpt = apprenantRepository.findById(apprenantId);
        Optional<SousCompetence> sousCompetenceOpt = sousCompetenceRepository.findById(sousCompetenceId);
        if (apprenantOpt.isPresent() && sousCompetenceOpt.isPresent()) {
            return validationService.validerSousCompetence(apprenantOpt.get(), sousCompetenceOpt.get(), statut);
        }
        throw new RuntimeException("Apprenant ou SousCompétence introuvable");
    }

    @GetMapping("/statut")
    public StatutValidation getStatut(@RequestParam Long apprenantId, @RequestParam Long sousCompetenceId) {
        Optional<Apprenant> apprenantOpt = apprenantRepository.findById(apprenantId);
        Optional<SousCompetence> sousCompetenceOpt = sousCompetenceRepository.findById(sousCompetenceId);
        if (apprenantOpt.isPresent() && sousCompetenceOpt.isPresent()) {
            return validationService.getValidation(apprenantOpt.get(), sousCompetenceOpt.get())
                    .map(ValidationSousCompetence::getStatut)
                    .orElse(StatutValidation.NON_VALIDE);
        }
        throw new RuntimeException("Apprenant ou SousCompétence introuvable");
    }
} 