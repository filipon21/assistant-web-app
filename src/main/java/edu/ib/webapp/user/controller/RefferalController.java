package edu.ib.webapp.user.controller;

import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import edu.ib.webapp.user.model.response.RefferalResponse;
import edu.ib.webapp.user.service.RefferalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Klasa do obsługi skierowań (kontroler Springowy)
 */
@RestController
@RequestMapping("/api/refferal")
@RequiredArgsConstructor
public class RefferalController {

    private final RefferalService refferalService;

    /**
     * Metoda służąca do obsługi zapytania dot. znalezienia wszystkich skierowań użytkownika
     * @param userId - id użytkownika (bazodanowe)
     * @param doctorSpecializationEnum - specjalizacja lekarza
     * @return skierowania użytkownika (lista)
     */
    @GetMapping("/list/{userId}")
    public List<RefferalResponse> getAllUserRefferals(@PathVariable Long userId,
                                                      @RequestParam(required = false) DoctorSpecializationEnum doctorSpecializationEnum){
        return refferalService.getAllUserRefferals(userId, doctorSpecializationEnum);
    }

    /**
     * Metoda służąca do obsługi zapytania dot. zapisynia skierowania dla użytkownika
     * @param visitId - id wizyty w której wystawiono skierowanie (bazodanowe)
     * @param doctorSpecializationEnum - specjalizacja lekarza do któego jest skierowanie
     * @return zwraca dane dot. skierowania
     */
    @PostMapping("/{visitId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    public RefferalResponse createRefferal(@PathVariable Long visitId,
                                           @RequestParam DoctorSpecializationEnum doctorSpecializationEnum){
        return refferalService.createRefferal(visitId, doctorSpecializationEnum);
    }

    /**
     * Metoda służąca do obsługi zapytania dot. usunięcia skierowania dla użytkownika
     * @param refferalId - id skierowania (bazodanowe)
     */
    @DeleteMapping("/{refferalId}")
    public void deleteRefferal(@PathVariable Long refferalId){
        refferalService.deleteRefferal(refferalId);
    }

    /**
     * Metoda służąca do obsługi zapytania dot. pobrania danego skierowania dla użytkownika
     * @param id skierowania (bazodanowe)
     * @return dane dot. skierowania
     */
    @GetMapping("/{id}")
    public RefferalResponse getRefferal(@PathVariable Long id) {
        return refferalService.getRefferal(id);
    }

}
