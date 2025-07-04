package com.ENAA.SKills.ENAA.SKills.controller;

import com.ENAA.SKills.ENAA.SKills.model.Apprenant;
import com.ENAA.SKills.ENAA.SKills.service.ApprenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.ENAA.SKills.ENAA.SKills.model.Competence;
import com.ENAA.SKills.ENAA.SKills.service.CompetenceService;
import com.ENAA.SKills.ENAA.SKills.dto.DashboardCompetenceDto;
import java.util.ArrayList;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/apprenants")
public class ApprenantController {
    @Autowired
    private ApprenantService apprenantService;
    @Autowired
    private CompetenceService competenceService;

    @PostMapping
    public Apprenant create(@RequestBody Apprenant apprenant) {
        return apprenantService.save(apprenant);
    }

    @PostMapping("/{apprenantId}/add-competence/{competenceId}")
    public ResponseEntity<String> addCompetenceToApprenant(@PathVariable Long apprenantId, @PathVariable Long competenceId) {
        Optional<Apprenant> apprenantOpt = apprenantService.findById(apprenantId);
        Optional<Competence> competenceOpt = competenceService.findById(competenceId);
        if (apprenantOpt.isPresent() && competenceOpt.isPresent()) {
            Apprenant apprenant = apprenantOpt.get();
            Competence competence = competenceOpt.get();
            apprenant.getCompetences().add(competence);
            apprenantService.save(apprenant);
            return ResponseEntity.ok("Competence ajoutée à l'apprenant !");
        }
        return ResponseEntity.status(404).body("Apprenant ou Competence introuvable");
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        // Créer le tri
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        // Créer la pagination
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // Récupérer les apprenants paginés
        Page<Apprenant> apprenants = apprenantService.findAllPaginated(pageable);
        
        return ResponseEntity.ok(apprenants);
    }

    @GetMapping("/{id}")
    public Optional<Apprenant> getById(@PathVariable Long id) {
        return apprenantService.findById(id);
    }

    @GetMapping("/{id}/dashboard")
    public DashboardCompetenceDto getDashboard(@PathVariable Long id) {
        Apprenant apprenant = apprenantService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apprenant not found"));
        List<Competence> allCompetences = competenceService.getAllCompetences();

        DashboardCompetenceDto dto = new DashboardCompetenceDto();
        dto.apprenantId = apprenant.getId();
        dto.nom = apprenant.getNom();
        dto.competences = new ArrayList<>();

        for (Competence c : allCompetences) {
            DashboardCompetenceDto.CompetenceEtat etat = new DashboardCompetenceDto.CompetenceEtat();
            etat.id = c.getId();
            etat.nom = c.getNom();
            etat.acquise = apprenant.getCompetences().contains(c);
            dto.competences.add(etat);
        }
        return dto;
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
    public ResponseEntity<String> delete(@PathVariable Long id) {
        apprenantService.deleteById(id);
        return ResponseEntity.ok("Apprenant supprimé avec succès");
    }
}