package com.ENAA.SKills.ENAA.SKills

import com.ENAA.SKills.ENAA.SKills.controller.ValidationSousCompetenceController
import com.ENAA.SKills.ENAA.SKills.model.Apprenant
import com.ENAA.SKills.ENAA.SKills.model.Competence
import com.ENAA.SKills.ENAA.SKills.model.SousCompetence
import com.ENAA.SKills.ENAA.SKills.model.StatutValidation
import com.ENAA.SKills.ENAA.SKills.model.ValidationSousCompetence
import com.ENAA.SKills.ENAA.SKills.repository.ApprenantRepository
import com.ENAA.SKills.ENAA.SKills.repository.SousCompetenceRepository
import com.ENAA.SKills.ENAA.SKills.service.ValidationSousCompetenceService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ResponseStatusException

import static org.junit.jupiter.api.Assertions.*
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.*

@ExtendWith(MockitoExtension.class)
class ValidationSousCompetenceControllerTest {

    @Mock
    private ValidationSousCompetenceService validationService

    @Mock
    private ApprenantRepository apprenantRepository

    @Mock
    private SousCompetenceRepository sousCompetenceRepository

    @InjectMocks
    private ValidationSousCompetenceController controller

    private Apprenant apprenant
    private Competence competence
    private SousCompetence sousCompetence
    private ValidationSousCompetence validation

    @BeforeEach
    void setUp() {
        // Créer les données de test
        apprenant = new Apprenant()
        apprenant.setId(1L)
        apprenant.setNom("Test Apprenant")
        apprenant.setEmail("test@example.com")

        competence = new Competence()
        competence.setId(1L)
        competence.setNom("Test Compétence")

        sousCompetence = new SousCompetence()
        sousCompetence.setId(1L)
        sousCompetence.setTitre("Test Sous-Compétence")
        sousCompetence.setCompetence(competence)
        sousCompetence.setValide(false)

        validation = new ValidationSousCompetence()
        validation.setId(1L)
        validation.setApprenant(apprenant)
        validation.setSousCompetence(sousCompetence)
        validation.setStatut(StatutValidation.VALIDE)
    }

    @Test
    void testValiderSousCompetence_Success() {
        // Arrange
        when(apprenantRepository.findById(1L)).thenReturn(Optional.of(apprenant))
        when(sousCompetenceRepository.findById(1L)).thenReturn(Optional.of(sousCompetence))
        when(validationService.validerSousCompetence(apprenant, sousCompetence, StatutValidation.VALIDE))
                .thenReturn(validation)

        // Act
        ValidationSousCompetence result = controller.validerSousCompetence(1L, 1L, StatutValidation.VALIDE)

        // Assert
        assertNotNull(result)
        assertEquals(StatutValidation.VALIDE, result.getStatut())
        assertEquals(apprenant.getId(), result.getApprenant().getId())
        assertEquals(sousCompetence.getId(), result.getSousCompetence().getId())

        verify(apprenantRepository).findById(1L)
        verify(sousCompetenceRepository).findById(1L)
        verify(validationService).validerSousCompetence(apprenant, sousCompetence, StatutValidation.VALIDE)
    }

    @Test
    void testValiderSousCompetence_ApprenantNotFound() {
        // Arrange
        when(apprenantRepository.findById(999L)).thenReturn(Optional.empty())
        when(sousCompetenceRepository.findById(1L)).thenReturn(Optional.of(sousCompetence))

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controller.validerSousCompetence(999L, 1L, StatutValidation.VALIDE)
        })

        assertEquals("Apprenant ou SousCompétence introuvable", exception.getMessage())
        verify(apprenantRepository).findById(999L)
        verify(sousCompetenceRepository).findById(1L)
        verify(validationService, never()).validerSousCompetence(any(), any(), any())
    }

    @Test
    void testValiderSousCompetence_SousCompetenceNotFound() {
        // Arrange
        when(apprenantRepository.findById(1L)).thenReturn(Optional.of(apprenant))
        when(sousCompetenceRepository.findById(999L)).thenReturn(Optional.empty())

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controller.validerSousCompetence(1L, 999L, StatutValidation.VALIDE)
        })

        assertEquals("Apprenant ou SousCompétence introuvable", exception.getMessage())
        verify(apprenantRepository).findById(1L)
        verify(sousCompetenceRepository).findById(999L)
        verify(validationService, never()).validerSousCompetence(any(), any(), any())
    }

    @Test
    void testGetStatut_Success_ValidationExists() {
        // Arrange
        when(apprenantRepository.findById(1L)).thenReturn(Optional.of(apprenant))
        when(sousCompetenceRepository.findById(1L)).thenReturn(Optional.of(sousCompetence))
        when(validationService.getValidation(apprenant, sousCompetence))
                .thenReturn(Optional.of(validation))

        // Act
        StatutValidation result = controller.getStatut(1L, 1L)

        // Assert
        assertEquals(StatutValidation.VALIDE, result)
        verify(apprenantRepository).findById(1L)
        verify(sousCompetenceRepository).findById(1L)
        verify(validationService).getValidation(apprenant, sousCompetence)
    }

    @Test
    void testGetStatut_Success_NoValidationExists() {
        // Arrange
        when(apprenantRepository.findById(1L)).thenReturn(Optional.of(apprenant))
        when(sousCompetenceRepository.findById(1L)).thenReturn(Optional.of(sousCompetence))
        when(validationService.getValidation(apprenant, sousCompetence))
                .thenReturn(Optional.empty())

        // Act
        StatutValidation result = controller.getStatut(1L, 1L)

        // Assert
        assertEquals(StatutValidation.NON_VALIDE, result)
        verify(apprenantRepository).findById(1L)
        verify(sousCompetenceRepository).findById(1L)
        verify(validationService).getValidation(apprenant, sousCompetence)
    }

    @Test
    void testGetStatut_ApprenantNotFound() {
        // Arrange
        when(apprenantRepository.findById(999L)).thenReturn(Optional.empty())
        when(sousCompetenceRepository.findById(1L)).thenReturn(Optional.of(sousCompetence))

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controller.getStatut(999L, 1L)
        })

        assertEquals("Apprenant ou SousCompétence introuvable", exception.getMessage())
        verify(apprenantRepository).findById(999L)
        verify(sousCompetenceRepository).findById(1L)
        verify(validationService, never()).getValidation(any(), any())
    }

    @Test
    void testGetStatut_SousCompetenceNotFound() {
        // Arrange
        when(apprenantRepository.findById(1L)).thenReturn(Optional.of(apprenant))
        when(sousCompetenceRepository.findById(999L)).thenReturn(Optional.empty())

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            controller.getStatut(1L, 999L)
        })

        assertEquals("Apprenant ou SousCompétence introuvable", exception.getMessage())
        verify(apprenantRepository).findById(1L)
        verify(sousCompetenceRepository).findById(999L)
        verify(validationService, never()).getValidation(any(), any())
    }

    @Test
    void testValiderSousCompetence_AllStatuts() {
        // Arrange
        when(apprenantRepository.findById(1L)).thenReturn(Optional.of(apprenant))
        when(sousCompetenceRepository.findById(1L)).thenReturn(Optional.of(sousCompetence))

        // Test pour chaque statut
        StatutValidation[] statuts = [StatutValidation.EN_COURS, StatutValidation.VALIDE, StatutValidation.NON_VALIDE]

        statuts.each { statut ->
            ValidationSousCompetence expectedValidation = new ValidationSousCompetence()
            expectedValidation.setId(1L)
            expectedValidation.setApprenant(apprenant)
            expectedValidation.setSousCompetence(sousCompetence)
            expectedValidation.setStatut(statut)

            when(validationService.validerSousCompetence(apprenant, sousCompetence, statut))
                    .thenReturn(expectedValidation)

            // Act
            ValidationSousCompetence result = controller.validerSousCompetence(1L, 1L, statut)

            // Assert
            assertNotNull(result)
            assertEquals(statut, result.getStatut())
            assertEquals(apprenant.getId(), result.getApprenant().getId())
            assertEquals(sousCompetence.getId(), result.getSousCompetence().getId())

            verify(validationService).validerSousCompetence(apprenant, sousCompetence, statut)
        }
    }

    @Test
    void testValiderSousCompetence_UpdateExistingValidation() {
        // Arrange
        ValidationSousCompetence existingValidation = new ValidationSousCompetence()
        existingValidation.setId(1L)
        existingValidation.setApprenant(apprenant)
        existingValidation.setSousCompetence(sousCompetence)
        existingValidation.setStatut(StatutValidation.EN_COURS)

        ValidationSousCompetence updatedValidation = new ValidationSousCompetence()
        updatedValidation.setId(1L)
        updatedValidation.setApprenant(apprenant)
        updatedValidation.setSousCompetence(sousCompetence)
        updatedValidation.setStatut(StatutValidation.VALIDE)

        when(apprenantRepository.findById(1L)).thenReturn(Optional.of(apprenant))
        when(sousCompetenceRepository.findById(1L)).thenReturn(Optional.of(sousCompetence))
        when(validationService.validerSousCompetence(apprenant, sousCompetence, StatutValidation.VALIDE))
                .thenReturn(updatedValidation)

        // Act
        ValidationSousCompetence result = controller.validerSousCompetence(1L, 1L, StatutValidation.VALIDE)

        // Assert
        assertNotNull(result)
        assertEquals(StatutValidation.VALIDE, result.getStatut())
        assertEquals(1L, result.getId())

        verify(validationService).validerSousCompetence(apprenant, sousCompetence, StatutValidation.VALIDE)
    }
} 