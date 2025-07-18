package com.ENAA.SKills.ENAA.SKills.service;

import com.ENAA.SKills.ENAA.SKills.model.Apprenant;
import com.ENAA.SKills.ENAA.SKills.repository.ApprenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

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

    // Méthode pour récupérer les apprenants avec pagination
    public Page<Apprenant> findAllPaginated(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Apprenant> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Apprenant getApprenantById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apprenant not found"));
    }
}
