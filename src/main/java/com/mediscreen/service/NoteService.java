package com.mediscreen.service;

import com.mediscreen.model.Note;
import com.mediscreen.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public List<Note> findByPatId(String patId) {
        return noteRepository.findByPatId(patId);
    }

    public Note save(Note newNote) {
        return noteRepository.save(newNote);
    }
}
