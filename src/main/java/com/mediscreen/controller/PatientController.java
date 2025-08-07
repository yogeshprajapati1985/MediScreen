package com.mediscreen.controller;

import com.mediscreen.dto.PatientResource;
import com.mediscreen.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/add")
    public ResponseEntity<PatientResource> addPatient(@RequestBody PatientResource patientResource) {
        return ResponseEntity.ok(patientService.savePatient(patientResource));
    }

    @PostMapping("/update")
    public ResponseEntity<PatientResource> updatePatient(@RequestBody PatientResource patientResource) {
        return ResponseEntity.ok(patientService.savePatient(patientResource));
    }

    @GetMapping("/find")
    public ResponseEntity<PatientResource> getPatientByName(@RequestParam String family, @RequestParam String given) {
        return patientService.getPatientByName(family, given)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public String showSearchForm(Model model) {
        model.addAttribute("patient", new PatientResource());
        return "search"; // Return the name of the template to render
    }

    @PostMapping("/search")
    public String searchPatient(@ModelAttribute PatientResource patient, Model model) {
        Optional<PatientResource> results = patientService.getPatientByName(patient.getFamily(), patient.getGiven());
        List<PatientResource> patientResources = results.map(List::of).orElse(List.of());
        model.addAttribute("results", patientResources);
        model.addAttribute("patient", patient);
        return "search";
    }

}
