package com.ENAA.SKills.ENAA.SKills.service;

import com.ENAA.SKills.ENAA.SKills.model.Competence;
import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;
import com.ENAA.SKills.ENAA.SKills.model.ValidationSousCompetence;
import com.ENAA.SKills.ENAA.SKills.model.StatutValidation;
import com.ENAA.SKills.ENAA.SKills.repository.CompetenceRepository;
import com.ENAA.SKills.ENAA.SKills.repository.ValidationSousCompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetenceService {
    private final CompetenceRepository repository;
    private final ValidationSousCompetenceRepository validationRepository;

    public CompetenceService(CompetenceRepository repository, ValidationSousCompetenceRepository validationRepository) {
        this.repository = repository;
        this.validationRepository = validationRepository;
    }

    public Competence save(Competence competence) {

        return repository.save(competence);
    }

    public List<Competence> findAll() {

        return repository.findAll();
    }

    public Optional<Competence> findById(Long id) {

        return repository.findById(id);
    }

    public void deleteById(Long id) {

        repository.deleteById(id);
    }

    public boolean isCompetenceAcquiredByApprenant(Long competenceId, Long apprenantId) {
        Competence competence = repository.findById(competenceId).orElse(null);
        if (competence == null) return false;
        List<SousCompetence> sousCompetences = competence.getSousCompetences();
        if (sousCompetences == null || sousCompetences.isEmpty()) return false;
        List<ValidationSousCompetence> validations = validationRepository.findAll();
        for (SousCompetence sc : sousCompetences) {
            boolean valide = validations.stream().anyMatch(v ->
                v.getApprenant().getId().equals(apprenantId) &&
                v.getSousCompetence().getId().equals(sc.getId()) &&
                v.getStatut() == StatutValidation.VALIDE
            );
            if (!valide) return false;
        }
        return true;
    }
}
