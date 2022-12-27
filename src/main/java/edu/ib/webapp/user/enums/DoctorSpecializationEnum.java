package edu.ib.webapp.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum typów specjalizacji doktorów
 */
@Getter
@RequiredArgsConstructor
public enum DoctorSpecializationEnum {
    ORTHOPAEDIST("ORTOPEDA"),
    NEUROLOGIST("NEUROLOG"),
    SURGEON("CHIRURG"),
    OPHTHALMOLOGIST("OKULISTA"),
    PSYCHIATRIST("PSYCHIATRA"),
    CARDIOLOGIST("KARDIOLOG"),
    PSYCHOLOGIST("PSYCHOLOG"),
    DENTIST("DENTYSTA"),
    INTERNIST("INTERNISTA"),
    DERMATOLOGIST("DERMATOLOG");

    private final String specialization;

}
