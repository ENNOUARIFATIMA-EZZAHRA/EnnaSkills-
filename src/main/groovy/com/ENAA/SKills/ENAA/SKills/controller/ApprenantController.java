package com.ENAA.SKills.ENAA.SKills.controller;

import com.ENAA.SKills.ENAA.SKills.model.Apprenant;
import com.ENAA.SKills.ENAA.SKills.service.ApprenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/apprenants")
public class ApprenantController {
    @Autowired
    private ApprenantService apprenantService;

    @PostMapping
    public Apprenant create(@RequestBody Apprenant apprenant) {
        return apprenantService.save(apprenant);
    }

    @GetMapping
    public List<Apprenant> getAll() {
        return apprenantService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Apprenant> getById(@PathVariable Long id) {
        return apprenantService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apprenant> update(@PathVariable Long id, @RequestBody Apprenant apprenant) {
        Optional<Apprenant> existing = apprenantService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        apprenant.setId(id);
        return ResponseEntity.ok(apprenantService.save(apprenant));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        apprenantService.deleteById(id);
    }
}