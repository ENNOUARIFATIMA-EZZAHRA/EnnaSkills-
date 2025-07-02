package com.ENAA.SKills.ENAA.SKills.dto;

import java.util.List;

public class ProgressionDto {
    public Long apprenantId;
    public String apprenantNom;
    public List<CompetenceProgression> competences;

    public static class CompetenceProgression {
        public Long id;
        public String nom;
        public boolean acquise;
        public List<SousCompetenceProgression> sousCompetences;
    }

    public static class SousCompetenceProgression {
        public Long id;
        public String titre;
        public String statut;
    }
} 