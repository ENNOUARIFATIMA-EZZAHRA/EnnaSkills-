package com.ENAA.SKills.ENAA.SKills.model;

import jakarta.persistence.*;

@Entity
public class ValidationSousCompetence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatutValidation statut;

    @ManyToOne
    @JoinColumn(name = "apprenant_id")
    private Apprenant apprenant;

    @ManyToOne
    @JoinColumn(name = "sous_competence_id")
    private SousCompetence sousCompetence;

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public StatutValidation getStatut() { return statut; }
    public void setStatut(StatutValidation statut) { this.statut = statut; }

    public Apprenant getApprenant() { return apprenant; }
    public void setApprenant(Apprenant apprenant) { this.apprenant = apprenant; }

    public SousCompetence getSousCompetence() { return sousCompetence; }
    public void setSousCompetence(SousCompetence sousCompetence) { this.sousCompetence = sousCompetence; }
} 