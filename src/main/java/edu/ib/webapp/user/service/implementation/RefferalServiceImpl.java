package edu.ib.webapp.user.service.implementation;

import edu.ib.webapp.common.exception.ExceptionMessage;
import edu.ib.webapp.common.exception.UserException;
import edu.ib.webapp.user.entity.Refferal;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.entity.Visit;
import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import edu.ib.webapp.user.enums.RefferalStatusEnum;
import edu.ib.webapp.user.mapper.RefferalMapper;
import edu.ib.webapp.user.model.response.RefferalResponse;
import edu.ib.webapp.user.repository.RefferalRepository;
import edu.ib.webapp.user.repository.UserRepository;
import edu.ib.webapp.user.repository.VisitRepository;
import edu.ib.webapp.user.service.RefferalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klasa służąca do przetworzenia logiki biznesowej związanej ze skierowaniami (serwis Springowy)
 */
@Service
@RequiredArgsConstructor
public class RefferalServiceImpl implements RefferalService {

    private final VisitRepository visitRepository;

    private final RefferalRepository refferalRepository;

    private final UserRepository userRepository;

    private final RefferalMapper refferalMapper;

    /**
     * Metoda służąca do tworzenia skierowań
     * @param visitId - id wizyty
     * @param doctorSpecializationEnum - specjalizacja lekarza
     * @return dane skierowania lub kod błędu 404 gdy nie odnaleziono wizyty
     */
    @Override
    @Transactional
    public RefferalResponse createRefferal(Long visitId, DoctorSpecializationEnum doctorSpecializationEnum) {
        Visit visit = findVisitById(visitId);
        if (visit == null){
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.VISIT_NOT_FOUND);
        }

        Refferal refferal = new Refferal();
        refferal.setDoctorSpecializationEnum(doctorSpecializationEnum);
        refferal.setEndTime(LocalDateTime.now().plusYears(1));
        refferal.setStatus(RefferalStatusEnum.ISSUED);
        refferal.setVisit(visit);
        refferalRepository.save(refferal);

        return refferalMapper.refferalToRefferalResponse(refferal);
    }

    /**
     * Metoda służąca do znalezienia wszystkich skierowań danego użytkownika
     * @param userId - id użytkownika
     * @param specialization - specjalizacja lekarza
     * @return lista skierowań
     */
    @Override
    public List<RefferalResponse> getAllUserRefferals(Long userId, DoctorSpecializationEnum specialization) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null){
            throw new UserException(HttpStatus.NOT_FOUND, ExceptionMessage.USER_NOT_FOUND);
        }
        List<Refferal> refferals;
        if (specialization != null){
           refferals = refferalRepository.findByVisit_Users_IdAndStatusAndDoctorSpecializationEnumOrderByEndTimeDesc
                   (userId, RefferalStatusEnum.ISSUED, specialization);
        }else{
            refferals = refferalRepository.findByVisit_Users_IdAndStatusOrderByEndTimeDesc(userId, RefferalStatusEnum.ISSUED);
        }
        return refferals.stream().map(refferalMapper::refferalToRefferalResponse).collect(Collectors.toList());
    }

    /**
     * Metoda służąca do znalezzienia danego skierowania
     * @param id - id skierowania (bazodanowe)
     * @return dane skierowania lub kod błędu 404 gdy nie znaleziono skierowania
     */
    @Override
    public RefferalResponse getRefferal(Long id) {

       Refferal refferal = refferalRepository.findById(id).orElse(null);
        if (refferal == null) {
            throw new UserException(HttpStatus.NOT_FOUND, ExceptionMessage.REFFERAL_NOT_FOUND);
        }
        return refferalMapper.refferalToRefferalResponse(refferal);
    }

    /**
     * Metoda służąca do usunięcia skierowania
     * @param refferalId - id skierowania
     */
    @Override
    @Transactional
    public void deleteRefferal(Long refferalId) {
        Refferal refferal = refferalRepository.findById(refferalId).orElse(null);
        if (refferal == null){
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.REFFERAL_NOT_FOUND);
        }
        this.refferalRepository.delete(refferal);
    }

    public Visit findVisitById(Long id){
        return visitRepository.findById(id).orElse(null);
    }
}
