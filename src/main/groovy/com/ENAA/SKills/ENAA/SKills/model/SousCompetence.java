package com.ENAA.SKills.ENAA.SKills.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class SousCompetence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Getter
    private boolean valide;

    @ManyToOne
    @JoinColumn(name = "competence_id")
    private Competence competence;


}
