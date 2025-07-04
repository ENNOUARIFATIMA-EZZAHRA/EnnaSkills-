package com.ENAA.SKills.ENAA.SKills.model;

import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;
import com.ENAA.SKills.ENAA.SKills.model.ValidationSousCompetence;
import com.ENAA.SKills.ENAA.SKills.model.StatutValidation;
import com.ENAA.SKills.ENAA.SKills.model.Apprenant;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Competence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "competence", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<SousCompetence> sousCompetences;

    @ManyToMany(mappedBy = "competences")
    private List<Apprenant> apprenants = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public List<SousCompetence> getSousCompetences() { return sousCompetences; }
    public void setSousCompetences(List<SousCompetence> sousCompetences) { this.sousCompetences = sousCompetences; }

    public boolean isValide() {
        if (sousCompetences == null || sousCompetences.isEmpty()) return false;
        return sousCompetences.stream().allMatch(SousCompetence::isValide);
    }

    public List<Apprenant> getApprenants() { return apprenants; }
    public void setApprenants(List<Apprenant> apprenants) { this.apprenants = apprenants; }
}
