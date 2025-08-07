package com.mediscreen.controller;

import com.mediscreen.model.Note;
import com.mediscreen.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patHistory")
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/add")
    public ResponseEntity<Note> addNote(@RequestParam String patId, @RequestParam String note) {

        Note newNote = new Note();
        newNote.setPatId(patId);
        newNote.setNote(note); // preserves whitespace
        return new ResponseEntity<>(noteService.save(newNote), HttpStatus.CREATED);
    }

    @GetMapping("/get/{patId}")
    public ResponseEntity<List<Note>> getNotesByPatient(@PathVariable String patId) {
        return new ResponseEntity<>(noteService.findByPatId(patId), HttpStatus.OK);
    }
}
