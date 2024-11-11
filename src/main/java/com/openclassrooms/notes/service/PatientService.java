package com.openclassrooms.notes.service;

import com.openclassrooms.notes.entity.Patient;

import java.util.List;

public interface PatientService {

    Patient updateNotes(Long patId, String note);

    Patient findAPatient(Long patId);

    List<Patient> findAllPatient();
//    Patient deleteNote(String patientId, String noteToDelete);

}
