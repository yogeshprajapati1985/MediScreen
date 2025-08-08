package com.mediscreen.service;

import com.mediscreen.dto.NoteResource;
import com.mediscreen.exception.NoteNotFoundException;
import com.mediscreen.mapper.PatientMapper;
import com.mediscreen.model.Note;
import com.mediscreen.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
                .map(patientMapper::toNoteResource)
                .toList();
    }

    public Note save(Note newNote) {
        return noteRepository.save(newNote);
    }

    public Note findById(ObjectId id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with id: " + id));
    }
}
