package edu.ib.webapp.user.controller;

import edu.ib.webapp.user.model.request.TelevisitRequest;
import edu.ib.webapp.user.model.response.TelevisitResponse;
import edu.ib.webapp.user.service.TelevisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@PreAuthorize("hasAnyRole('USER', 'ASSISTANT')")
@RequestMapping("/api/visit")
public class TelevisitController {

    private final TelevisitService televisitService;

    @PostMapping("/{userId}/{assistantId}")
    public TelevisitResponse createVisit(@RequestBody TelevisitRequest televisitRequest, @PathVariable Long userId,
                                         @PathVariable Long assistantId, Authentication authentication) {
        return televisitService.createVisit(televisitRequest, userId, assistantId, authentication.getName());
    }

    @PatchMapping("/{id}")
    public TelevisitResponse updateVisit(@PathVariable Long id, @RequestBody TelevisitRequest televisitRequest) {
        return televisitService.updateVisit(id, televisitRequest);
    }

    @GetMapping("/list/{userId}")
    public List<TelevisitResponse> getUserVisits(@PathVariable Long userId){
        return televisitService.getUserVisits(userId);
    }

    @GetMapping("/{id}")
    public TelevisitResponse getVisit(@PathVariable Long id){
        return televisitService.getVisit(id);
    }
}
