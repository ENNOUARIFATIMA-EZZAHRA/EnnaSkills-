package com.ENAA.SKills.ENAA.SKills.model;

import jakarta.persistence.*;

@Entity
public class SousCompetence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private boolean valide;

    @ManyToOne
    @JoinColumn(name = "competence_id")
    private Competence competence;

   
    public boolean isValide() {
        return this.valide;
    }

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public boolean getValide() { return valide; }
    public void setValide(boolean valide) { this.valide = valide; }
    public Competence getCompetence() { return competence; }
    public void setCompetence(Competence competence) { this.competence = competence; }
}
