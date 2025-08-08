package com.mediscreen.repository;

import com.mediscreen.model.Note;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends MongoRepository<Note, Long> {
    List<Note> findByPatId(Long patId);
    Optional<Note> findById(ObjectId id); // Query by MongoDB internal _id
}
