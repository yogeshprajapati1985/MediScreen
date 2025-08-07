package com.mediscreen.controller;

import com.mediscreen.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/assess")
public class AssessmentController {
    private final AssessmentService assessmentService;

    @PostMapping("/{patId}")
    public ResponseEntity<String> assessPatientById(@PathVariable String patId) {
        return ResponseEntity.ok(assessmentService.assess(patId));
    }
}
