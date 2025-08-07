package com.mediscreen.service;

import com.mediscreen.constant.MediscreenConstants;
import com.mediscreen.model.Note;
import com.mediscreen.util.MediscreenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final NoteService noteService;
    private final PatientService patientService;

    public String assess(String patId) {

        // Fetch patient details
        var patientOpt = patientService.getPatientById(Long.parseLong(patId));
        if (patientOpt.isEmpty()) {
            return "Patient not found with ID: " + patId;
        }
        var patient = patientOpt.get();

        List<Note> noteList = noteService.findByPatId(patId); // Fetch notes for the patient
        if (noteList.isEmpty()) {
            return "No notes found for patient ID: " + patId;
        } else {
            int triggerCount = 0;
            for (Note note : noteList) {
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
