package com.mediscreen.service;

import com.mediscreen.dto.PatientResource;
import com.mediscreen.mapper.PatientMapper;
import com.mediscreen.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public Optional<PatientResource> getPatientByName(String family, String given) {
        return patientRepository.findByFamilyNameAndGivenName(family, given)
                .map(patientMapper::toPatientResource);
    }
    public PatientResource savePatient(PatientResource patientResource) {
        return patientMapper.toPatientResource(patientRepository.save(patientMapper.toPatient(patientResource)));
    }

    public Optional<PatientResource> getPatientById(Long patId) {
        return patientRepository.findById(patId).map(patientMapper::toPatientResource);
    }
}
