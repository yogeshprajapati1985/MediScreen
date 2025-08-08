package com.mediscreen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoteResource {
    private Long patId;
    private String note;
}
