package com.ENAA.SKills.ENAA.SKills.dto;

import com.ENAA.SKills.ENAA.SKills.model.Apprenant;

public class ApprenantMapper {
    public static ApprenantDto toDto(Apprenant apprenant) {
        if (apprenant == null) return null;
        ApprenantDto dto = new ApprenantDto();
        dto.setId(apprenant.getId());
        dto.setNom(apprenant.getNom());
        dto.setEmail(apprenant.getEmail());
        return dto;
    }

    public static Apprenant toEntity(ApprenantDto dto) {
        if (dto == null) return null;
        Apprenant apprenant = new Apprenant();
        apprenant.setId(dto.getId());
        apprenant.setNom(dto.getNom());
        apprenant.setEmail(dto.getEmail());
        return apprenant;
    }
} 