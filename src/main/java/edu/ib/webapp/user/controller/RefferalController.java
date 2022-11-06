package edu.ib.webapp.user.controller;

import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import edu.ib.webapp.user.model.response.RefferalResponse;
import edu.ib.webapp.user.service.RefferalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refferal")
@RequiredArgsConstructor
public class RefferalController {

    private final RefferalService refferalService;

    @GetMapping("/list/{userId}")
    public List<RefferalResponse> getAllUserRefferals(@PathVariable Long userId,
                                                      @RequestParam(required = false) DoctorSpecializationEnum doctorSpecializationEnum){
        return refferalService.getAllUserRefferals(userId, doctorSpecializationEnum);
    }

    @PostMapping("/{visitId}")
    public RefferalResponse createRefferal(@PathVariable Long visitId,
                                           @RequestParam DoctorSpecializationEnum doctorSpecializationEnum){
        return refferalService.createRefferal(visitId, doctorSpecializationEnum);
    }

    @DeleteMapping("/{refferalId}")
    public void deleteRefferal(@PathVariable Long refferalId){
        refferalService.deleteRefferal(refferalId);
    }

    @GetMapping("/{id}")
    public RefferalResponse getRefferal(@PathVariable Long id) {
        return refferalService.getRefferal(id);
    }

}
