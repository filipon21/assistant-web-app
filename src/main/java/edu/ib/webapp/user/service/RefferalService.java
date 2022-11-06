package edu.ib.webapp.user.service;

import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import edu.ib.webapp.user.model.response.RefferalResponse;

import java.util.List;

public interface RefferalService {

    RefferalResponse createRefferal(Long visitId, DoctorSpecializationEnum doctorSpecializationEnum);

    List<RefferalResponse> getAllUserRefferals(Long userId, DoctorSpecializationEnum specialization);

    void deleteRefferal(Long refferalId);

    RefferalResponse getRefferal(Long id);
}
