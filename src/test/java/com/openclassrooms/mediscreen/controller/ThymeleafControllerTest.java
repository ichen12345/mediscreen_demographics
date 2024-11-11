package com.openclassrooms.mediscreen.controller;

import com.openclassrooms.mediscreen.entity.Patient;
import com.openclassrooms.mediscreen.service.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ThymeleafController.class)
public class ThymeleafControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Test
    void testListPatients() throws Exception {
        Mockito.when(patientService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("all"))
                .andExpect(model().attributeExists("patients"));
    }

    @Test
    void testEditPatientForm() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);
        Mockito.when(patientService.findAPatient(1L)).thenReturn(patient);

        mockMvc.perform(get("/patients/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("patient", patient));
    }

    @Test
    void testUpdatePatient() throws Exception {
        mockMvc.perform(post("/patients/update")
                        .param("id", "1")
                        .param("name", "Test Patient")
                        .param("dob", "1990-01-01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));

        Mockito.verify(patientService).updatePatient(any(Patient.class));
    }

    @Test
    void testShowAddPatientForm() throws Exception {
        mockMvc.perform(get("/patients/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    void testAddPatient() throws Exception {
        mockMvc.perform(post("/patients/add")
                        .param("name", "New Patient")
                        .param("dob", "2000-01-01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));

        Mockito.verify(patientService).addPatient(any(Patient.class));
    }
}
