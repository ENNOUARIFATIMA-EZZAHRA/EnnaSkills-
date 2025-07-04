package com.ENAA.SKills.ENAA.SKills.dto;

import java.util.List;

public class DashboardCompetenceDto {
    public Long apprenantId;
    public String nom;
    public List<CompetenceEtat> competences;

    public static class CompetenceEtat {
        public Long id;
        public String nom;
        public boolean acquise;
    }
} 