package com.ENAA.SKills.ENAA.SKills;

import com.ENAA.SKills.ENAA.SKills.model.Competence;
import com.ENAA.SKills.ENAA.SKills.repository.CompetenceRepository;
import com.ENAA.SKills.ENAA.SKills.repository.ValidationSousCompetenceRepository;
import com.ENAA.SKills.ENAA.SKills.service.CompetenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CompetenceServiceTest {

    private CompetenceRepository competenceRepository;
    private ValidationSousCompetenceRepository validationSousCompetenceRepository;
    private CompetenceService competenceService;

    @BeforeEach
    public void setUp() {
        competenceRepository = mock(CompetenceRepository.class);
        validationSousCompetenceRepository = mock(ValidationSousCompetenceRepository.class);
        competenceService = new CompetenceService(competenceRepository, validationSousCompetenceRepository);
    }

    @Test
    public void testSaveCompetence() {
        // données d'entrée
        Competence c = new Competence();
        c.setNom("Programmation Java");

        // résultat attendu
        Competence saved = new Competence();
        saved.setId(1L);
        saved.setNom("Programmation Java");

        // définir comportement du mock
        when(competenceRepository.save(c)).thenReturn(saved);

        // appel
        Competence result = competenceService.save(c);

        // vérification
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Programmation Java", result.getNom());

        // s'assurer que la méthode save a été appelée 1 seule fois
        verify(competenceRepository, times(1)).save(c);
    }
}
//pour lancer les tests : mvn test