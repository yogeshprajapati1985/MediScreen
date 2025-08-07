package com.mediscreen.mapper;

import com.mediscreen.dto.PatientResource;
import com.mediscreen.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Named("stringToLocalDate")
    static LocalDate mapDob(String dob) {
        return LocalDate.parse(dob, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Mapping(target = "patientId", source = "id")
    @Mapping(target = "givenName", source = "given")
    @Mapping(target = "familyName", source = "family")
    @Mapping(target = "dob", source = "dob", qualifiedByName = "stringToLocalDate")
    Patient toPatient(PatientResource request);

    @Mapping(target = "id", source = "patientId")
    @Mapping(target = "given", source = "givenName")
    @Mapping(target = "family", source = "familyName")
    @Mapping(target = "dob", source = "dob", dateFormat = "yyyy-MM-dd")
    PatientResource toPatientResource(Patient patient);
}

