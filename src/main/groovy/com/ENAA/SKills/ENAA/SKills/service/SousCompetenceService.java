package com.ENAA.SKills.ENAA.SKills.service;

import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;
import com.ENAA.SKills.ENAA.SKills.repository.SousCompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SousCompetenceService {
    @Autowired
    private SousCompetenceRepository repository;

    public SousCompetence save(SousCompetence sousCompetence) {
        return repository.save(sousCompetence);
    }

    public List<SousCompetence> findAll() {
        return repository.findAll();
    }

    public Optional<SousCompetence> findById(Long id) {

        return repository.findById(id);
    }

    public void deleteById(Long id) {

        repository.deleteById(id);
    }
}
