package edu.ib.webapp.user.controller;

import edu.ib.webapp.user.model.request.ExemptionRequest;
import edu.ib.webapp.user.model.response.ExemptionResponse;
import edu.ib.webapp.user.service.ExemptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exemption")
public class ExemptionController {

    private final ExemptionService exemptionService;

    @PostMapping("/{visitId}")
    public ExemptionResponse createExemption(@PathVariable Long visitId, @RequestBody ExemptionRequest exemptionRequest){
        return exemptionService.createExamination(visitId, exemptionRequest);
    }

}
