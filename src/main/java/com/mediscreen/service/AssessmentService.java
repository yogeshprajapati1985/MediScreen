package com.mediscreen.service;

import com.mediscreen.constant.MediscreenConstants;
import com.mediscreen.dto.NoteResource;
import com.mediscreen.dto.PatientHistoryResource;
import com.mediscreen.exception.PatientNotFoundException;
import com.mediscreen.util.MediscreenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final WebClient webClient;

    public String assess(Long patId) {

        PatientHistoryResource patientHistoryResource = webClient.get()
                .uri("/patHistory/get/{patId}", patId)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse ->
                        Mono.error(new PatientNotFoundException("Patient not found with id: " + patId)))
                .bodyToMono(PatientHistoryResource.class)
                .block();


        var patient = Objects.requireNonNull(patientHistoryResource).getPatient();

        List<NoteResource> noteList = patientHistoryResource.getNotes();
        if (noteList.isEmpty()) {
            return "No notes found for patient ID: " + patId;
        } else {
            int triggerCount = 0;
            for (NoteResource note : noteList) {
                String content = note.getNote().toLowerCase();
                // Count the number of trigger terms in the note content from constants
                triggerCount += MediscreenUtil.countMatchingTerms(content, MediscreenConstants.TRIGGER_TERMS);
            }

            //Determine the risk levels
            // 1. None - patient has no doctor’s notes containing any of the trigger terminology.
            // 2. Borderline - patient has a reference to two triggers, and is over 30 years of age.
            // 3. In danger - depends on patient’s age and sex.
            // If under 30, and male, then three trigger terms.
            // If under 30 and female, four trigger terms. If either over 30, then six.
            // 4. Early onset -
            // If under 30, and male, then five trigger terms.
            // If under 30 and female, seven trigger terms. If over 30, then eight or more.
            String riskLevel = "None"; // Default risk level

            if (triggerCount == 0) {
                riskLevel = "None";
            } else if (triggerCount == 2 && MediscreenUtil.getAge(patient.getDob()) > 30) {
                riskLevel = "Borderline";
            } else if ((MediscreenUtil.getAge(patient.getDob()) < 30 && patient.getSex().equals("M") && triggerCount >= 3 && triggerCount < 5) ||
                       (MediscreenUtil.getAge(patient.getDob()) < 30 && patient.getSex().equals("F") && triggerCount >= 4 && triggerCount < 7) ||
                       (MediscreenUtil.getAge(patient.getDob()) >= 30 && triggerCount == 6)) {
                riskLevel = "In danger";
            } else if ((MediscreenUtil.getAge(patient.getDob()) < 30 && patient.getSex().equals("M") && triggerCount >= 5) ||
                       (MediscreenUtil.getAge(patient.getDob()) < 30 && patient.getSex().equals("F") && triggerCount >= 7) ||
                       (MediscreenUtil.getAge(patient.getDob()) >= 30 && triggerCount >= 8)) {
                riskLevel = "Early onset";
            }

            //Result format: Patient: Test TestNone (age 52) diabetes assessment is: None
            return String.format("Patient: %s %s (%s) diabetes assessment is: %s",
                    patient.getGiven(), patient.getFamily(),
                    MediscreenUtil.getAge(patient.getDob()), riskLevel);
        }
    }
}
