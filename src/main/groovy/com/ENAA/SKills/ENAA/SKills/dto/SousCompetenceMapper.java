package com.ENAA.SKills.ENAA.SKills.dto;

import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;

public class SousCompetenceMapper {
    public static SousCompetenceDto toDto(SousCompetence sousCompetence) {
        if (sousCompetence == null) return null;
        SousCompetenceDto dto = new SousCompetenceDto();
        dto.setId(sousCompetence.getId());
        dto.setNom(sousCompetence.getTitre());
        return dto;
    }

    public static SousCompetence toEntity(SousCompetenceDto dto) {
        if (dto == null) return null;
        SousCompetence sousCompetence = new SousCompetence();
        sousCompetence.setId(dto.getId());
        sousCompetence.setTitre(dto.getNom());
        return sousCompetence;
    }
} 