package com.openclassrooms.notes.service;

import com.openclassrooms.notes.entity.Patient;
import com.openclassrooms.notes.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Use Mockito extension
public class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl patientService;

    @Mock
    private PatientRepository patientRepository;

    private Patient patient;

    @BeforeEach
    public void setUp() {
        patient = new Patient("someId", 2L, new ArrayList<>()); // Initialize a Patient object
    }

    @Test
    public void testUpdateNotes_PatientFound() {
        String newNote = "New patient note";
        patient.getNote().add("Existing note");

        // Mock the behavior of the repository
        when(patientRepository.findByPatId(2L)).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);

        // Call the method under test
        Patient updatedPatient = patientService.updateNotes(2L, newNote);

        // Verify the results
        assertNotNull(updatedPatient);
        assertEquals(2, updatedPatient.getNote().size());
        assertTrue(updatedPatient.getNote().contains(newNote));
        verify(patientRepository, times(1)).findByPatId(2L);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void testUpdateNotes_PatientNotFound() {
        // Mock the behavior of the repository to return empty
        when(patientRepository.findByPatId(2L)).thenReturn(Optional.empty());

        // Call the method under test and expect an exception
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            patientService.updateNotes(2L, "New note");
        });

        // Verify the exception message
        assertEquals("Patient not found with ID: 2", exception.getMessage());
        verify(patientRepository, times(1)).findByPatId(2L);
        verify(patientRepository, times(0)).save(any());
    }

    @Test
    public void testFindAPatient_Success() {
        // Mock the repository to return an Optional of patient when patId is found
        when(patientRepository.findByPatId(2L)).thenReturn(Optional.of(patient));

        // Call the method under test
        Patient result = patientService.findAPatient(2L);

        // Verify the results
        assertNotNull(result);
        assertEquals(patient, result);
        verify(patientRepository, times(1)).findByPatId(2L);
    }

    @Test
    public void testFindAPatient_NotFound() {
        // Mock the repository to return an empty Optional when patId is not found
        when(patientRepository.findByPatId(2L)).thenReturn(Optional.empty());

        // Call the method under test and expect an exception
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            patientService.findAPatient(2L);
        });

        // Verify the exception message and behavior
        assertEquals("Patient not found", exception.getMessage());
        verify(patientRepository, times(1)).findByPatId(2L);
    }
}

