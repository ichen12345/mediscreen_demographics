package com.openclassrooms.notes.controller;

import com.openclassrooms.notes.entity.Patient;
import com.openclassrooms.notes.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patHistory")
public class ThymeleafController {
    @Autowired
    private PatientService patientService;

    @GetMapping("")
    public String listPatients(Model model) {
        List<Patient> patients = patientService.findAllPatient();
        model.addAttribute("patients", patients);
        return "patients"; // Thymeleaf template for the main page
    }

    @GetMapping("/update/{patId}")
    public String updatePatientForm(@PathVariable Long patId, Model model) {
        Patient patient = patientService.findAPatient(patId); // Fetch the patient by ID
        model.addAttribute("patient", patient);
        return "update"; // Thymeleaf template for the update page
    }

    @PostMapping("/update/{patId}")
    public String updatePatient(@PathVariable Long patId, @RequestParam String note) {
        patientService.updateNotes(patId, note); // Update notes for the patient
        return "redirect:/patHistory"; // Redirect to the patients list page
    }
}
