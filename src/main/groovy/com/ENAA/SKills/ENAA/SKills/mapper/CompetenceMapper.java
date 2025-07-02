package com.ENAA.SKills.ENAA.SKills.dto;

import com.ENAA.SKills.ENAA.SKills.model.Competence;

public class CompetenceMapper {
    public static CompetenceDto toDto(Competence competence) {
        if (competence == null) return null;
        CompetenceDto dto = new CompetenceDto();
        dto.setId(competence.getId());
        dto.setNom(competence.getNom());
       
        return dto;
    }

    public static Competence toEntity(CompetenceDto dto) {
        if (dto == null) return null;
        Competence competence = new Competence();
        competence.setId(dto.getId());
        competence.setNom(dto.getNom());
       
        return competence;
    }
} 