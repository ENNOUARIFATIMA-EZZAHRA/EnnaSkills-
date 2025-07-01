package com.ENAA.SKills.ENAA.SKills.service;

import com.ENAA.SKills.ENAA.SKills.model.ValidationSousCompetence;
import com.ENAA.SKills.ENAA.SKills.model.StatutValidation;
import com.ENAA.SKills.ENAA.SKills.model.Apprenant;
import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;
import com.ENAA.SKills.ENAA.SKills.repository.ValidationSousCompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidationSousCompetenceService {
    @Autowired
    private ValidationSousCompetenceRepository repository;

    public ValidationSousCompetence validerSousCompetence(Apprenant apprenant, SousCompetence sousCompetence, StatutValidation statut) {
        Optional<ValidationSousCompetence> existing = repository.findAll().stream()
            .filter(v -> v.getApprenant().getId().equals(apprenant.getId()) && v.getSousCompetence().getId().equals(sousCompetence.getId()))
            .findFirst();
        ValidationSousCompetence validation;
        if (existing.isPresent()) {
            validation = existing.get();
            validation.setStatut(statut);
        } else {
            validation = new ValidationSousCompetence();
            validation.setApprenant(apprenant);
            validation.setSousCompetence(sousCompetence);
            validation.setStatut(statut);
        }
        return repository.save(validation);
    }

    public Optional<ValidationSousCompetence> getValidation(Apprenant apprenant, SousCompetence sousCompetence) {
        return repository.findAll().stream()
            .filter(v -> v.getApprenant().getId().equals(apprenant.getId()) && v.getSousCompetence().getId().equals(sousCompetence.getId()))
            .findFirst();
    }
} 