package com.ENAA.SKills.ENAA.SKills.model;

import com.ENAA.SKills.ENAA.SKills.model.SousCompetence;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Competence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "competence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SousCompetence> sousCompetences;
    public boolean isValide() {
        if (sousCompetences == null || sousCompetences.isEmpty()) return false;
        return sousCompetences.stream().allMatch(SousCompetence::isValide);
    }


}
