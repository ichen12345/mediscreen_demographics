package com.openclassrooms.notes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    private String id; // Change this to String for MongoDB ObjectId support

    private Long patId;

    private List<String> note;

    public Patient(Long patId, List<String> note) {
        this.patId = patId;
        this.note = note;
    }

}
