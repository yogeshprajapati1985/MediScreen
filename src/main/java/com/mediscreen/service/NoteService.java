package com.mediscreen.service;

import com.mediscreen.dto.NoteResource;
import com.mediscreen.mapper.PatientMapper;
import com.mediscreen.model.Note;
import com.mediscreen.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final PatientMapper patientMapper;

    public List<NoteResource> findByPatId(Long patId) {
        return noteRepository.findByPatId(patId)
                .stream()
                .map(note -> new NoteResource(patId, note.getNote()))
                .toList();
    }

    public Note save(Note newNote) {
        return noteRepository.save(newNote);
    }
}
