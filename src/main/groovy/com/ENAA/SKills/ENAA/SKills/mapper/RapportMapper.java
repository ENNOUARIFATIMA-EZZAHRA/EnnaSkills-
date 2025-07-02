package com.ENAA.SKills.ENAA.SKills.mapper;

import com.ENAA.SKills.ENAA.SKills.dto.RapportDto;
import com.ENAA.SKills.ENAA.SKills.model.Rapport;

public class RapportMapper {
    public static RapportDto toDto(Rapport rapport) {
        if (rapport == null) return null;
        RapportDto dto = new RapportDto();
        dto.setTitle(rapport.getTitle());
        dto.setContent(rapport.getContent());
        dto.setAuthor(rapport.getAuthor());
        return dto;
    }

    public static Rapport toEntity(RapportDto dto) {
        if (dto == null) return null;
        Rapport rapport = new Rapport();
        rapport.setTitle(dto.getTitle());
        rapport.setContent(dto.getContent());
        rapport.setAuthor(dto.getAuthor());
        return rapport;
    }
} 