package com.openclassrooms.notes.controller;

import com.openclassrooms.notes.entity.Patient;
import com.openclassrooms.notes.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patHistory")
public class PatientController {
    @Autowired
    PatientService patientService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK) 
    public ResponseEntity<Patient> addNote(@RequestParam Long patId, @RequestParam String note) {
        try {
            Patient updatedPatient = patientService.updateNotes(patId, note);
            return ResponseEntity.ok(updatedPatient);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Error case with no body
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> findAPatient(@PathVariable("id") Long patId) {
        try {
            Patient patient = patientService.findAPatient(patId);
            return ResponseEntity.ok(patient);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Error case with no body
        }
    }

}
