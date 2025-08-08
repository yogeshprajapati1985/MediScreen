package com.mediscreen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoteResource {
    private String id;
    private Long patId;
    private String note;
}
