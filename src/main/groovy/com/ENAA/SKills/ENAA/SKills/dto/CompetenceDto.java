package com.ENAA.SKills.ENAA.SKills.dto;

public class CompetenceDto {
    private Long id;
    private String nom;
    private String description;
    private String niveau;
    private String type;

    public CompetenceDto() {
    }

    public CompetenceDto(Long id, String nom, String description, String niveau, String type) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.niveau = niveau;
        this.type = type;
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

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
