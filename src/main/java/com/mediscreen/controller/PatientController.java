package com.mediscreen.controller;

import com.mediscreen.dto.PatientResource;
import com.mediscreen.exception.PatientNotFoundException;
import com.mediscreen.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/add")
    public ResponseEntity<PatientResource> addPatient(@RequestBody @Validated PatientResource patientResource) {
        return ResponseEntity.ok(patientService.savePatient(patientResource));
    }

    @PostMapping("/update")
    public ResponseEntity<PatientResource> updatePatient(@RequestBody @Validated PatientResource patientResource) {
        return ResponseEntity.ok(patientService.savePatient(patientResource));
    }

    @GetMapping("/find")
    public ResponseEntity<PatientResource> getPatientByName(@RequestParam String family, @RequestParam String given) throws NoResourceFoundException {
        MDC.put("param", family + ", " + given);
        MDC.put("requestId", UUID.randomUUID().toString());

        ResponseEntity<PatientResource> response = patientService.getPatientByName(family, given)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with name: " + family + ", " + given));

        MDC.clear();

        return response;
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
