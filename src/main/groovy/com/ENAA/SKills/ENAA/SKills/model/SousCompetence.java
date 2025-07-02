package com.ENAA.SKills.ENAA.SKills.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Competence competence;

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public boolean getValide() { return valide; }
    public void setValide(boolean valide) { this.valide = valide; }

    public boolean isValide() {
        return this.valide;
    }

    public Competence getCompetence() { return competence; }
    public void setCompetence(Competence competence) { this.competence = competence; }
}
