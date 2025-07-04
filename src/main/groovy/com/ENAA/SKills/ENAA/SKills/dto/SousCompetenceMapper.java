package com.ENAA.SKills.ENAA.SKills.dto;

import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;
import com.ENAA.SKills.ENAA.SKills.model.Competence;
import com.ENAA.SKills.ENAA.SKills.service.CompetenceService;
import org.springframework.beans.factory.annotation.Autowired;

public class SousCompetenceMapper {
    public static SousCompetenceDto toDto(SousCompetence sousCompetence) {
        if (sousCompetence == null) return null;
        SousCompetenceDto dto = new SousCompetenceDto();
        dto.setId(sousCompetence.getId());
        dto.setNom(sousCompetence.getTitre());
        return dto;
    }

    public static SousCompetence toEntity(SousCompetenceDto dto, CompetenceService competenceService) {
        if (dto == null) return null;
        SousCompetence sousCompetence = new SousCompetence();
        sousCompetence.setId(dto.getId());
        sousCompetence.setTitre(dto.getNom());
        if (dto.getCompetenceId() != null) {
            competenceService.findById(dto.getCompetenceId()).ifPresent(sousCompetence::setCompetence);
        }
        return sousCompetence;
    }
} 