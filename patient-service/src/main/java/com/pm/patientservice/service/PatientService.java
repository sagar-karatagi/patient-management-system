package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exceptions.EmailAlreadyExistException;
import com.pm.patientservice.exceptions.PatientNotFoundException;

import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
//    private final BillingServiceGrpcClient billingServiceGrpcClient;


    public PatientService(PatientRepository patientRepository) {
//        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDto).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistException("Patient with this email already exist", patientRequestDTO.getEmail());
        }
        // First, convert DTO to an entity
        Patient patient = PatientMapper.toModel(patientRequestDTO);
        // Save the entity
        Patient savedPatient = patientRepository.save(patient);
        // Convert back to DTO and return

//        billingServiceGrpcClient.createBillingAccount(patient.getId().toString(),patient.getName().toString(),patient.getEmail().toString());
        return PatientMapper.toDto(savedPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            Patient existingPatient = patient.get();

            // Check if the new email already exists for a different patient
            if (!existingPatient.getEmail().equals(patientRequestDTO.getEmail()) && patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
                throw new EmailAlreadyExistException("Patient with this email already exist", patientRequestDTO.getEmail());
            }

            // Update the existing patient with new data
            PatientMapper.updatePatientFromDto(patientRequestDTO, existingPatient);
            existingPatient.setId(id); // Preserve the original ID
            // Save and return the updated patient
            Patient savedPatient = patientRepository.save(existingPatient);
            return PatientMapper.toDto(savedPatient);
        }

        throw new PatientNotFoundException("Patient not found with id: " + id);
    }


    public void deletePatient(UUID id) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

}