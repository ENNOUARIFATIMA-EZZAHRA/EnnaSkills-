package com.ENAA.SKills.ENAA.SKills.controller;

import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;
import com.ENAA.SKills.ENAA.SKills.model.Competence;
import com.ENAA.SKills.ENAA.SKills.service.SousCompetenceService;
import com.ENAA.SKills.ENAA.SKills.service.CompetenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sous-competences")
public class SousCompetenceController {
    @Autowired
    private SousCompetenceService sousCompetenceService;
    @Autowired
    private CompetenceService competenceService;

    public static class SousCompetenceCreationDto {
        public String titre;
        public boolean valide;
        public Long competenceId;
    }

    @PostMapping
    public SousCompetence create(@RequestBody SousCompetenceCreationDto dto) {
        SousCompetence sousCompetence = new SousCompetence();
        sousCompetence.setTitre(dto.titre);
        sousCompetence.setValide(dto.valide);
        if (dto.competenceId != null) {
            competenceService.findById(dto.competenceId).ifPresent(sousCompetence::setCompetence);
        }
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
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody SousCompetenceCreationDto dto) {
        Optional<SousCompetence> existing = sousCompetenceService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(404).body("Aucune sous-compétence trouvée avec l'ID " + id);
        }
        SousCompetence sousCompetence = existing.get();
        sousCompetence.setTitre(dto.titre);
        sousCompetence.setValide(dto.valide);
        if (dto.competenceId != null) {
            competenceService.findById(dto.competenceId).ifPresent(sousCompetence::setCompetence);
        }
        sousCompetenceService.save(sousCompetence);
        return ResponseEntity.ok("Sous-compétence modifiée avec succès");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        sousCompetenceService.deleteById(id);
    }
}
