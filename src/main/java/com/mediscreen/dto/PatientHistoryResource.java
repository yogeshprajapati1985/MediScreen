package com.mediscreen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PatientHistoryResource {
    private PatientResource patient;
    private List<NoteResource> notes;
}
