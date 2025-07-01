package com.ENAA.SKills.ENAA.SKills.controller;

import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;
import com.ENAA.SKills.ENAA.SKills.service.SousCompetenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sous-competences")
public class SousCompetenceController {
    @Autowired
    private SousCompetenceService sousCompetenceService;

    @PostMapping
    public SousCompetence create(@RequestBody SousCompetence sousCompetence) {
        return sousCompetenceService.save(sousCompetence);
    }

    @GetMapping
    public List<SousCompetence> getAll() {
        return sousCompetenceService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<SousCompetence> getById(@PathVariable Long id) {
        return sousCompetenceService.findById(id);
    }

    @PutMapping("/{id}")
    public SousCompetence update(@PathVariable Long id, @RequestBody SousCompetence sousCompetence) {
        sousCompetence.setId(id);
        return sousCompetenceService.save(sousCompetence);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        sousCompetenceService.deleteById(id);
    }
}
