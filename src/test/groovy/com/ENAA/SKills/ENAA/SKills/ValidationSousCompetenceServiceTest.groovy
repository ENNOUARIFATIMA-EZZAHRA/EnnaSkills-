package com.ENAA.SKills.ENAA.SKills

import com.ENAA.SKills.ENAA.SKills.model.Apprenant
import com.ENAA.SKills.ENAA.SKills.model.Competence
import com.ENAA.SKills.ENAA.SKills.model.SousCompetence
import com.ENAA.SKills.ENAA.SKills.model.StatutValidation
import com.ENAA.SKills.ENAA.SKills.model.ValidationSousCompetence
import com.ENAA.SKills.ENAA.SKills.repository.ValidationSousCompetenceRepository
import com.ENAA.SKills.ENAA.SKills.service.ValidationSousCompetenceService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

import static org.junit.jupiter.api.Assertions.*
import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.*

@ExtendWith(MockitoExtension.class)
class ValidationSousCompetenceServiceTest {

    @Mock
    private ValidationSousCompetenceRepository repository

    @InjectMocks
    private ValidationSousCompetenceService service

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
    void testValiderSousCompetence_NewValidation() {
        // Arrange
        when(repository.findAll()).thenReturn([])
        when(repository.save(any(ValidationSousCompetence.class))).thenReturn(validation)

        // Act
        ValidationSousCompetence result = service.validerSousCompetence(apprenant, sousCompetence, StatutValidation.VALIDE)

        // Assert
        assertNotNull(result)
        assertEquals(StatutValidation.VALIDE, result.getStatut())
        assertEquals(apprenant.getId(), result.getApprenant().getId())
        assertEquals(sousCompetence.getId(), result.getSousCompetence().getId())

        verify(repository).findAll()
        verify(repository).save(any(ValidationSousCompetence.class))
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

        when(repository.findAll()).thenReturn([existingValidation])
        when(repository.save(any(ValidationSousCompetence.class))).thenReturn(updatedValidation)

        // Act
        ValidationSousCompetence result = service.validerSousCompetence(apprenant, sousCompetence, StatutValidation.VALIDE)

        // Assert
        assertNotNull(result)
        assertEquals(StatutValidation.VALIDE, result.getStatut())
        assertEquals(1L, result.getId())

        verify(repository).findAll()
        verify(repository).save(any(ValidationSousCompetence.class))
    }

    @Test
    void testGetValidation_ValidationExists() {
        // Arrange
        when(repository.findAll()).thenReturn([validation])

        // Act
        Optional<ValidationSousCompetence> result = service.getValidation(apprenant, sousCompetence)

        // Assert
        assertTrue(result.isPresent())
        assertEquals(validation.getId(), result.get().getId())
        assertEquals(validation.getStatut(), result.get().getStatut())

        verify(repository).findAll()
    }

    @Test
    void testGetValidation_NoValidationExists() {
        // Arrange
        when(repository.findAll()).thenReturn([])

        // Act
        Optional<ValidationSousCompetence> result = service.getValidation(apprenant, sousCompetence)

        // Assert
        assertFalse(result.isPresent())

        verify(repository).findAll()
    }

    @Test
    void testGetValidation_MultipleValidations() {
        // Arrange
        ValidationSousCompetence otherValidation = new ValidationSousCompetence()
        otherValidation.setId(2L)
        otherValidation.setApprenant(new Apprenant(id: 2L))
        otherValidation.setSousCompetence(new SousCompetence(id: 2L))
        otherValidation.setStatut(StatutValidation.NON_VALIDE)

        when(repository.findAll()).thenReturn([validation, otherValidation])

        // Act
        Optional<ValidationSousCompetence> result = service.getValidation(apprenant, sousCompetence)

        // Assert
        assertTrue(result.isPresent())
        assertEquals(validation.getId(), result.get().getId())
        assertEquals(validation.getStatut(), result.get().getStatut())

        verify(repository).findAll()
    }

    @Test
    void testGetAll() {
        // Arrange
        List<ValidationSousCompetence> validations = [validation]
        when(repository.findAll()).thenReturn(validations)

        // Act
        List<ValidationSousCompetence> result = service.getAll()

        // Assert
        assertNotNull(result)
        assertEquals(1, result.size())
        assertEquals(validation.getId(), result.get(0).getId())

        verify(repository).findAll()
    }

    @Test
    void testValiderSousCompetence_AllStatuts() {
        // Arrange
        when(repository.findAll()).thenReturn([])
        when(repository.save(any(ValidationSousCompetence.class))).thenAnswer { invocation ->
            ValidationSousCompetence saved = invocation.getArgument(0)
            saved.setId(1L)
            return saved
        }

        // Test pour chaque statut
        StatutValidation[] statuts = [StatutValidation.EN_COURS, StatutValidation.VALIDE, StatutValidation.NON_VALIDE]

        statuts.each { statut ->
            // Act
            ValidationSousCompetence result = service.validerSousCompetence(apprenant, sousCompetence, statut)

            // Assert
            assertNotNull(result)
            assertEquals(statut, result.getStatut())
            assertEquals(apprenant.getId(), result.getApprenant().getId())
            assertEquals(sousCompetence.getId(), result.getSousCompetence().getId())
        }

        verify(repository, times(3)).findAll()
        verify(repository, times(3)).save(any(ValidationSousCompetence.class))
    }

    @Test
    void testValiderSousCompetence_DifferentApprenants() {
        // Arrange
        Apprenant apprenant2 = new Apprenant()
        apprenant2.setId(2L)
        apprenant2.setNom("Test Apprenant 2")
        apprenant2.setEmail("test2@example.com")

        ValidationSousCompetence validation2 = new ValidationSousCompetence()
        validation2.setId(2L)
        validation2.setApprenant(apprenant2)
        validation2.setSousCompetence(sousCompetence)
        validation2.setStatut(StatutValidation.NON_VALIDE)

        when(repository.findAll()).thenReturn([validation, validation2])
        when(repository.save(any(ValidationSousCompetence.class))).thenReturn(validation2)

        // Act
        ValidationSousCompetence result = service.validerSousCompetence(apprenant2, sousCompetence, StatutValidation.NON_VALIDE)

        // Assert
        assertNotNull(result)
        assertEquals(StatutValidation.NON_VALIDE, result.getStatut())
        assertEquals(apprenant2.getId(), result.getApprenant().getId())
        assertEquals(sousCompetence.getId(), result.getSousCompetence().getId())

        verify(repository).findAll()
        verify(repository).save(any(ValidationSousCompetence.class))
    }
} 