package edu.ib.webapp.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DoctorSpecializationEnum {
    ORTHOPAEDIST("ORTOPEDA"),
    NEUROLOGIST("NEUROLOG"),
    PEDIATRICIAN("PEDIATRA"),
    SURGEON("CHIRURG"),
    OPHTHALMOLOGIST("OKULISTA"),
    PSYCHIATRIST("PSYCHIATRA"),
    CARDIOLOGIST("KARDIOLOG"),
    PSYCHOLOGIST("PSYCHOLOG"),
    INTERNIST("INTERNISTA"),
    DERMATOLOGIST("DERMATOLOG");

    private final String specialization;

}
