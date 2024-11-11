package com.openclassrooms.notes.service;

import com.openclassrooms.notes.entity.Patient;
import com.openclassrooms.notes.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService{

    @Autowired
    PatientRepository patientRepository;

    @Override
    public Patient updateNotes(Long patId, String newNote) {
        Patient original = patientRepository.findByPatId(patId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patId));

        original.getNote().add(newNote);

        return patientRepository.save(original);

    }

    @Override
    public Patient findAPatient(Long patId) {
        Optional<Patient> patientOpt = patientRepository.findByPatId(patId);
        if (patientOpt.isEmpty()) {
            System.out.println("Patient with patId " + patId + " not found in the database.");
        }
        return patientOpt.orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    }

    @Override
    public List<Patient> findAllPatient() {
        return patientRepository.findAll();
    }

//    @Override
//    public Patient deleteNote(Long patientId, String noteToDelete) {
//        Patient original = patientRepository.findById(patientId)
//                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));
//
//        List<String> notes = original.getNote();
//        if (notes.remove(noteToDelete)) {
//            return patientRepository.save(original);
//        } else {
//            throw new IllegalArgumentException("Note not found: " + noteToDelete);
//        }
//    }
}
