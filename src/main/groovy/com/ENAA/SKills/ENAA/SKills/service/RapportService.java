package com.ENAA.SKills.ENAA.SKills.service;

import com.ENAA.SKills.ENAA.SKills.model.Rapport;
import com.ENAA.SKills.ENAA.SKills.repository.RapportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RapportService {
    @Autowired
    private RapportRepository rapportRepository;

    public Rapport save(Rapport rapport) {
        return rapportRepository.save(rapport);
    }
}
