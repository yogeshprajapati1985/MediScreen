package com.mediscreen.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PatientResource {
    private Long id;
    private String family;
    private String given;

    @NotNull(message = "Birthdate is required.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Birth date must be in YYYY-MM-DD format.")
    private String dob;

    @Pattern(regexp = "^[MF]$", message = "Sex must be either 'M' or 'F'")
    private String sex;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;
}
