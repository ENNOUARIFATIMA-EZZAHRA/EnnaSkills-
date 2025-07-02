package com.ENAA.SKills.ENAA.SKills.dto;

public class SousCompetenceDto {
    private Long id;
    private String nom;
    private String description;
    private Long competenceId;

    public SousCompetenceDto() {
    }

    public SousCompetenceDto(Long id, String nom, String description, Long competenceId) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.competenceId = competenceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(Long competenceId) {
        this.competenceId = competenceId;
    }
}
