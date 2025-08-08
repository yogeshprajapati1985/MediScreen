package com.mediscreen.controller;

import com.mediscreen.dto.NoteResource;
import com.mediscreen.dto.PatientHistoryResource;
import com.mediscreen.dto.PatientResource;
import com.mediscreen.exception.PatientNotFoundException;
import com.mediscreen.model.Note;
import com.mediscreen.service.NoteService;
import com.mediscreen.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patHistory")
public class NoteController {

    private final NoteService noteService;
    private final PatientService patientService;

    @PostMapping("/add")
    public ResponseEntity<Note> addNote(@RequestParam Long patId, @RequestParam String note) {

        patientService.getPatientById(patId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + patId));

        Note newNote = new Note();
        newNote.setPatId(patId);
        newNote.setNote(note); // preserves whitespace
        return new ResponseEntity<>(noteService.save(newNote), HttpStatus.CREATED);
    }

    @GetMapping("/get/{patId}")
    public ResponseEntity<PatientHistoryResource> getNotesByPatient(@PathVariable Long patId) {

        PatientResource patientResource;
        Optional<PatientResource> optionalPatientResource = patientService.getPatientById(patId);

        if(optionalPatientResource.isPresent()) {
            patientResource = optionalPatientResource.get();
        } else {
            throw new PatientNotFoundException("Patient not found with id: " + patId);
        }

        List<NoteResource> notes = noteService.findByPatId(patId);
        return ResponseEntity.ok(new PatientHistoryResource(patientResource, notes));
    }

    @PostMapping("/update")
    public ResponseEntity<Note> updateNote(@RequestParam ObjectId id, @RequestParam String note) {
        Note existingNote = noteService.findById(id);
        existingNote.setNote(note); // preserves whitespace
        return ResponseEntity.ok(noteService.save(existingNote));
    }
}
