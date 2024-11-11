package com.openclassrooms.notes.controller;

import com.openclassrooms.notes.entity.Patient;
import com.openclassrooms.notes.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    public void setUp() {
        patient = new Patient("someId", 2L, new ArrayList<>()); // Initialize a Patient object
    }

    @Test
    public void testAddNote_Success() {
        String note = "New patient note";

        // Mock the behavior of the service to return a Patient object
        when(patientService.updateNotes(2L, note)).thenReturn(patient);

        // Call the controller method
        ResponseEntity<Patient> response = patientController.addNote(2L, note);

        // Verify the results
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Check for 200 OK status
        assertNotNull(response.getBody());
        assertEquals(patient, response.getBody()); // Validate that the returned patient matches the mock

        verify(patientService, times(1)).updateNotes(2L, note);
    }

    @Test
    public void testAddNote_PatientNotFound() {
        String note = "New patient note";

        // Mock the behavior of the service to throw an EntityNotFoundException
        when(patientService.updateNotes(2L, note)).thenThrow(new EntityNotFoundException("Patient not found with ID: 2"));

        // Call the controller method and capture the response
        ResponseEntity<Patient> response = patientController.addNote(2L, note);

        // Verify the response status and body for failure
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Check for 404 NOT_FOUND status
        assertNull(response.getBody()); // Expect no body in case of failure

        verify(patientService, times(1)).updateNotes(2L, note);
    }


    @Test
    public void testFindAPatient_Success() {
        // Mock the service to return the patient
        when(patientService.findAPatient(2L)).thenReturn(patient);

        // Call the controller method
        ResponseEntity<Patient> response = patientController.findAPatient(2L);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Check status is 200 OK
        assertNotNull(response.getBody());
        assertEquals(patient, response.getBody());
        verify(patientService, times(1)).findAPatient(2L);
    }

    @Test
    public void testFindAPatient_NotFound() {
        // Mock the service to throw EntityNotFoundException
        when(patientService.findAPatient(2L)).thenThrow(new EntityNotFoundException("Patient not found with ID: 2"));

        // Call the controller method
        ResponseEntity<Patient> response = patientController.findAPatient(2L);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Check status is 404 NOT FOUND
        assertNull(response.getBody());
        verify(patientService, times(1)).findAPatient(2L);
    }

}