package com.pm.patientservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Entity class representing a patient in the healthcare system.
 * This class maintains the core patient information and is persisted in the database.
 *
 * @author Your Organization
 * @version 1.0
 */
@Entity
@Data
public class Patient {
    
    /**
     * Unique identifier for the patient.
     * Automatically generated using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * The full name of the patient.
     * This field is mandatory and cannot be null.
     */
    @NotNull
    private String name;

    /**
     * The email address of the patient.
     * Must be unique in the system and conform to email format standards.
     * This field is mandatory and cannot be null.
     */
    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    /**
     * The patient's date of birth.
     * This field is mandatory and cannot be null.
     */
    @NotNull
    private LocalDate dateOfBirth;

    /**
     * The date when the patient was registered in the system.
     * This field is mandatory and cannot be null.
     */
    @NotNull
    private LocalDate registeredDate;

    @NotNull
    private String address;
}