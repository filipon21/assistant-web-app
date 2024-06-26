package edu.ib.webapp.user.service.implementation;

import edu.ib.webapp.common.exception.ExceptionMessage;
import edu.ib.webapp.common.exception.UserException;
import edu.ib.webapp.user.entity.Exemption;
import edu.ib.webapp.user.entity.Visit;
import edu.ib.webapp.user.mapper.ExemptionMapper;
import edu.ib.webapp.user.model.request.ExemptionRequest;
import edu.ib.webapp.user.model.response.ExemptionResponse;
import edu.ib.webapp.user.repository.ExemptionRepository;
import edu.ib.webapp.user.repository.VisitRepository;
import edu.ib.webapp.user.service.ExemptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
/**
 * Klasa służąca do przetworzenia logiki biznesowej związanej ze zwolnieniami (serwis Springowy)
 */
@Service
@RequiredArgsConstructor
public class ExemptionServiceImpl implements ExemptionService {

    private final VisitRepository visitRepository;

    private final ExemptionRepository exemptionRepository;

    private final ExemptionMapper exemptionMapper;

    /**
     * Metoda do tworzenia zwolnienia (dostępna tylko dla lekarza i asystenta)
     * @param visitId - id wizyty (bazodanowe)
     * @param exemptionRequest - dane potrzebne do utworzenia zwolnienia
     * @return dane dot. utworzonego zwolnienia lub błąd 404 w przypadku nie znalezienia wizyty lub
     * błąd 403 w przypadku podania błędnego przedziału czasowego
     */
    @Override
    @Transactional
    public ExemptionResponse createExamination(Long visitId, ExemptionRequest exemptionRequest) {
        Visit visit = findVisitById(visitId);
        if (visit == null){
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.VISIT_NOT_FOUND);
        }
        long seconds = ChronoUnit.SECONDS.between(exemptionRequest.getStartTime(), exemptionRequest.getEndTime());

        if (seconds <= 0){
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.TIME_IS_NOT_CORRECT);
        }

        Exemption exemption = new Exemption();
        exemption.setStartTime(exemptionRequest.getStartTime());
        exemption.setEndTime(exemptionRequest.getEndTime());
        exemptionRepository.save(exemption);

        visit.setExemption(exemption);

        return exemptionMapper.exemptionToExemptionResponse(exemption);
    }

    public Visit findVisitById(Long id){
        return visitRepository.findById(id).orElse(null);
    }
}
