package com.mediscreen.dto;

import lombok.Data;

@Data
public class PatientResource {
    private Long id;
    private String family;
    private String given;
    private String dob;
    private String sex;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;
}
