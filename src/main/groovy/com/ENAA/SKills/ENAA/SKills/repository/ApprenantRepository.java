package com.ENAA.SKills.ENAA.SKills.repository;

import com.ENAA.SKills.ENAA.SKills.model.Apprenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprenantRepository extends JpaRepository<Apprenant, Long> {
    // Méthode pour récupérer les apprenants avec pagination
    Page<Apprenant> findAll(Pageable pageable);
}
