package com.ENAA.SKills.ENAA.SKills.service;

import com.ENAA.SKills.ENAA.SKills.model.Apprenant;
import com.ENAA.SKills.ENAA.SKills.repository.ApprenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprenantService {
    @Autowired
    private ApprenantRepository repository;

    public Apprenant save(Apprenant apprenant) {
        return repository.save(apprenant);
    }

    public List<Apprenant> findAll() {
        return repository.findAll();
    }

    public Optional<Apprenant> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
