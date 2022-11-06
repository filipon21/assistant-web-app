package edu.ib.webapp.user.service;

import edu.ib.webapp.user.model.request.ExemptionRequest;
import edu.ib.webapp.user.model.response.ExemptionResponse;

public interface ExemptionService {

    ExemptionResponse createExamination(Long visitId, ExemptionRequest exemptionRequest);

}
