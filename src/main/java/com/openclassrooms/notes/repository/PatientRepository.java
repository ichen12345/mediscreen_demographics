package com.openclassrooms.notes.repository;

import com.openclassrooms.notes.entity.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PatientRepository extends MongoRepository<Patient, String> {
    Optional<Patient> findByPatId(Long patId);
}
