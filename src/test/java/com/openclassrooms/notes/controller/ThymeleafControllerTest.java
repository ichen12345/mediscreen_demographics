package com.openclassrooms.notes.controller;

import com.openclassrooms.notes.entity.Patient;
import com.openclassrooms.notes.repository.PatientRepository;
import com.openclassrooms.notes.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ThymeleafController.class) // Ensures only the ThymeleafController is loaded for the test
public class ThymeleafControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Autowired MockMvc to simulate HTTP requests

    @MockBean
    private PatientService patientService; // Mocking the PatientService (it will also mock the PatientRepository dependency)

    @MockBean
    private PatientRepository patientRepository;

    @BeforeEach
    public void setUp() {
        // No need for explicit MockMvc setup because @WebMvcTest automatically configures it
    }

    @Test
    public void testListPatients() throws Exception {
        // Given
        Patient patient = new Patient(1L, Collections.singletonList("Test note"));
        List<Patient> patients = Collections.singletonList(patient);
        when(patientService.findAllPatient()).thenReturn(patients);  // Mocking the service method

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/patHistory"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))  // Verifying the view name
                .andExpect(model().attributeExists("patients"))  // Verifying the model attribute
                .andExpect(model().attribute("patients", patients));  // Verifying the attribute content

        verify(patientService, times(1)).findAllPatient();  // Verifying the service method was called once
    }

    @Test
    public void testUpdatePatientForm() throws Exception {
        // Given
        Long patId = 1L;
        Patient patient = new Patient(patId, Collections.singletonList("Test note"));
        when(patientService.findAPatient(patId)).thenReturn(patient);  // Mocking the service method

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/patHistory/update/{patId}", patId))
                .andExpect(status().isOk())
                .andExpect(view().name("update"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("patient", patient));

        verify(patientService, times(1)).findAPatient(patId);
    }

    @Test
    public void testUpdatePatient() throws Exception {
        // Given
        Long patId = 1L;
        String newNote = "Updated note";
        Patient patient = new Patient(patId, Collections.singletonList("Test note"));
        when(patientService.updateNotes(patId, newNote)).thenReturn(patient);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/patHistory/update/{patId}", patId)
                        .param("note", newNote))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patHistory"));  // Verifying the redirect URL

        verify(patientService, times(1)).updateNotes(patId, newNote);
    }
}
