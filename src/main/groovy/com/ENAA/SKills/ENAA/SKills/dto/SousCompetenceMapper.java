package com.ENAA.SKills.ENAA.SKills.dto;

import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;

public class SousCompetenceMapper {
    public static SousCompetenceDto toDto(SousCompetence sousCompetence) {
        if (sousCompetence == null) return null;
        SousCompetenceDto dto = new SousCompetenceDto();
        dto.setId(sousCompetence.getId());
        dto.setNom(sousCompetence.getTitre()); // Adapter si le champ s'appelle titre dans l'entité
        // dto.setDescription(...); // à adapter si le champ existe dans l'entité
        // dto.setCompetenceId(...); // à adapter si la relation existe
        return dto;
    }

    public static SousCompetence toEntity(SousCompetenceDto dto) {
        if (dto == null) return null;
        SousCompetence sousCompetence = new SousCompetence();
        sousCompetence.setId(dto.getId());
        sousCompetence.setTitre(dto.getNom()); // Adapter si le champ s'appelle titre dans l'entité
        // sousCompetence.setDescription(...); // à adapter si le champ existe dans l'entité
        // sousCompetence.setCompetenceId(...); // à adapter si la relation existe
        return sousCompetence;
    }
} 