package com.ENAA.SKills.ENAA.SKills.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Apprenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    @ManyToMany
    @JoinTable(
        name = "apprenant_competence",
        joinColumns = @JoinColumn(name = "apprenant_id"),
        inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private List<Competence> competences = new ArrayList<>();

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Competence> getCompetences() { return competences; }
    public void setCompetences(List<Competence> competences) { this.competences = competences; }
}
