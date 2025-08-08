package com.mediscreen.model;

import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "notes")
public class Note {
    @Id
    private String id;
    private Long patId;
    @Lob // Ensures large text is handled
    private String note;
    private LocalDateTime createdAt = LocalDateTime.now();
}

