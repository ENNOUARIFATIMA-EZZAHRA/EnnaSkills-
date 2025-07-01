package com.ENAA.SKills.ENAA.SKills.service;

import com.ENAA.SKills.ENAA.SKills.model.Competence;
import com.ENAA.SKills.ENAA.SKills.repository.CompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetenceService {
    @Autowired
    private CompetenceRepository repository;

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
}
