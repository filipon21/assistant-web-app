package edu.ib.webapp.user.service.implementation;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import edu.ib.webapp.common.exception.ExceptionMessage;
import edu.ib.webapp.common.exception.UserException;
import edu.ib.webapp.user.entity.Refferal;
import edu.ib.webapp.user.entity.Visit;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import edu.ib.webapp.user.enums.RefferalStatusEnum;
import edu.ib.webapp.user.enums.VisitStatusEnum;
import edu.ib.webapp.user.enums.VisitTypeEnum;
import edu.ib.webapp.user.mapper.VisitMapper;
import edu.ib.webapp.user.model.dto.VisitInfoDto;
import edu.ib.webapp.user.model.dto.VisitPaginationDto;
import edu.ib.webapp.user.model.request.VisitRequest;
import edu.ib.webapp.user.model.response.VisitListResponse;
import edu.ib.webapp.user.model.response.VisitResponse;
import edu.ib.webapp.user.repository.RefferalRepository;
import edu.ib.webapp.user.repository.UserRepository;
import edu.ib.webapp.user.repository.VisitRepository;
import edu.ib.webapp.user.repository.specification.FreeVisitSpecification;
import edu.ib.webapp.user.repository.specification.HistoryVisitSpecification;
import edu.ib.webapp.user.repository.specification.UpcomingVisitSpecification;
import edu.ib.webapp.user.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static edu.ib.webapp.user.pagination.VisitPaginationSupport.getPageRequest;

/**
 * Klasa służąca do przetworzenia logiki biznesowej związanej z wizytami (serwis Springowy)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    private final VisitMapper visitMapper;

    private final UserRepository userRepository;

    private final RefferalRepository refferalRepository;

    /**
     * Metoda służąca do tworzenia telewizyty w czasie rzeczywistym przez pacjenta z asystentem
     * @param visitRequest - dane związane z wizytą
     * @param userId - id pacjenta (bazodanowe)
     * @param hostId - id pracownika (bazodanowe)
     * @param username - nazwa użytkownika pacjenta
     * @return utworzona wizyta (Visit)
     */
    @Override
    @Transactional
    public VisitResponse createTeleVisit(VisitRequest visitRequest, Long userId, Long hostId, String username) {

        User userCheck = checkUserInfo(userId, username);

        User hostCheck = userRepository.findById(hostId).orElse(null);

        if (hostCheck == null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.WORKER_NOT_FOUND);
        }

        Visit visitsCheck = visitRepository.findFirstByUsers_IdAndVisitStatusEnumOrVisitStatusEnumOrVisitStatusEnumOrderByStartTimeDesc
                (userId, VisitStatusEnum.WAITING, VisitStatusEnum.UPCOMING, VisitStatusEnum.STARTED);

        if (visitsCheck != null) {
            long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), visitsCheck.getStartTime());
            if (minutes < 30) {
                throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.CANNOT_CREATE_NEW_VISIT);
            }
        }

        Visit visit = visitMapper.visitRequestToVisit(visitRequest);

        List<Visit> hostCheckVisits = hostCheck.getVisits();
        hostCheckVisits.add(visit);

        List<Visit> visits = userCheck.getVisits();
        visits.add(visit);

        VisitResponse visitResponse = visitMapper.visitToVisitResponse(visitRepository.save(visit));

        log.info(String.format("[Visit id: %s][Visit type: %s] Successful created new visit",
                visit.getId(),
                visit.getVisitTypeEnum())
        );

        return visitResponse;
    }

    /**
     * Metoda służąca do utworzenia wizyty przez pracownika
     * @param visitRequest - dane potrzebne do utworzenia wizyty
     * @param hostId - id pracwonika (bazodanowwe)
     * @return utwrzoona wizyta (Visit)
     */
    @Override
    @Transactional
    public VisitResponse createVisit(VisitRequest visitRequest, Long hostId) {
        User hostCheck = userRepository.findById(hostId).orElse(null);
        if (hostCheck == null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.WORKER_NOT_FOUND);
        }
        Visit visit = visitMapper.visitRequestToVisit(visitRequest);
        List<Visit> hostCheckVisits = hostCheck.getVisits();
        hostCheckVisits.add(visit);
        visit.setStartTime(visitRequest.getStartTime().plusHours(1));
        return visitMapper.visitToVisitResponse(visitRepository.save(visit));
    }

    /**
     * Metoda służąca do dodania pacjenta do danej wizyty
     * @param refferalId - skierowanie do wizyty (opcjonalne)
     * @param userId - id pacjenta (bazodanowe)
     * @param visitId - id wizyty (bazodanowe)
     * @return dana wizyta (Visit)
     */
    @Override
    @Transactional
    public VisitResponse addUserToVisit(Long refferalId, Long userId, Long visitId) {
        User userCheck = findUserById(userId);

        Refferal refferal = findRefferalById(refferalId);

        Visit visitCheck = findVisitById(visitId);
        List<User> users = visitCheck.getUsers();

        if (users.size() >= 2) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.CANNOT_ADD_NEW_USER);
        }

        if (users.get(0).getDoctor() != null){
            if (users.get(0).getDoctor().getDoctorSpecializationEnum() != DoctorSpecializationEnum.INTERNIST && refferal == null) {
                throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.REFFERAL_IS_NEEDED);
            }
        }

        if (refferal != null) {
            if (refferal.getDoctorSpecializationEnum() != visitCheck.getUsers().get(0).getDoctor().getDoctorSpecializationEnum()) {
                throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.BAD_REFFERAL);
            }
            refferal.setStatus(RefferalStatusEnum.COMPLETED);
            visitCheck.setRefferalId(refferalId);
            refferalRepository.save(refferal);
        }

        visitCheck.setVisitStatusEnum(VisitStatusEnum.UPCOMING);
        List<Visit> userVisits = userCheck.getVisits();
        userVisits.add(visitCheck);
        userRepository.save(userCheck);
        visitRepository.save(visitCheck);
        return visitMapper.visitToVisitResponse(visitCheck);
    }

    /**
     * Metoda służąca do usuwania pacjenta z danej wizyty
     * @param refferalId - id skierowania (opcjonalne)
     * @param userId - id użytkownika (bazodanowe)
     * @param visitId - id wizyty (bazodanowe)
     * @return daną wizytę (Visit)
     */
    @Override
    @Transactional
    public VisitResponse deleteUserFromVisit(Long refferalId, Long userId, Long visitId) {
        User userCheck = findUserById(userId);

        Refferal refferal = findRefferalById(refferalId);

        Visit visitCheck = findVisitById(visitId);

        List<Visit> visits = userCheck.getVisits();

        visits.remove(visitCheck);
        userRepository.save(userCheck);

        if (refferal != null) {
            refferal.setStatus(RefferalStatusEnum.ISSUED);
            refferalRepository.save(refferal);
        }
        log.info("usunieto uzytkownika z wizyty");

        visitCheck.setRefferalId(null);
        visitRepository.save(visitCheck);

        visitCheck.setVisitStatusEnum(VisitStatusEnum.FREE);

        return visitMapper.visitToVisitResponse(visitCheck);
    }

    /**
     * Metoda służąca do edycji wizyty przez pracownika
     * @param id - id wizyty (bazodanowe)
     * @param visitRequest - dane do edycji wizyty
     * @return wizyta (Visit)
     */
    @Override
    @Transactional
    public VisitResponse updateVisit(Long id, VisitRequest visitRequest) {
        Visit visitCheck = findVisitById(id);
        VisitStatusEnum visitStatusEnum = visitRequest.getVisitStatusEnum();
        VisitTypeEnum visitTypeEnum = visitRequest.getVisitTypeEnum();

        if (visitStatusEnum != null) {
            visitCheck.setVisitStatusEnum(visitRequest.getVisitStatusEnum());
            if (visitStatusEnum.equals(VisitStatusEnum.ENDED)) {
                visitCheck.setEndTime(LocalDateTime.now());
            }
        }
        if (visitTypeEnum != null) {
            visitCheck.setVisitTypeEnum(visitRequest.getVisitTypeEnum());
        }
        visitCheck.setRecommendation(visitRequest.getRecommendation());
        visitCheck.setChatLink(visitRequest.getChatLink());
        visitCheck.setDescription(visitRequest.getDescription());
        visitRepository.save(visitCheck);

        log.info("Zaaktualizowano wizytę");

        return visitMapper.visitToVisitResponse(visitCheck);
    }

    /**
     * Metoda słuząca do znalezienia aktualnych telewizyt użytkownika
     * @param id - id użytkownika
     * @return lista wizyt (Visit)
     */
    @Override
    public VisitResponse getUserTeleVisit(Long id) {
        Visit visit = visitRepository.
                findFirstByUsers_IdAndVisitStatusEnumAndVisitTypeEnumIsNotOrVisitTypeEnumIsNotAndVisitStatusEnumOrderByStartTimeDesc(
                        id, VisitStatusEnum.STARTED, VisitTypeEnum.STATIONARY, VisitTypeEnum.STATIONARY, VisitStatusEnum.WAITING);
        log.info("Znaleziono telewizyty");

        return visitMapper.visitToVisitResponse(visit);
    }

    /**
     * Metoda służąca do znalezienia wszystkich aktualnych wizyt użytkownika
     * @param id - id użytkownika (bazodanowe)
     * @return wizyta (Visit)
     */
    @Override
    public VisitResponse getUserStartedVisit(Long id) {
        Visit visit = visitRepository.
                findFirstByUsers_IdAndVisitStatusEnum(
                        id, VisitStatusEnum.STARTED);
        log.info("Znaleziono aktualną wizytę!");

        return visitMapper.visitToVisitResponse(visit);
    }

    /**
     * Metoda służąca do znalezienia wizyty
     * @param id - id wizyty (bazodanowe)
     * @return wizyta (Visit)
     */
    @Override
    public VisitResponse getVisit(Long id) {
        Visit visit = findVisitById(id);
        return visitMapper.visitToVisitResponse(visit);
    }

    /**
     * Metoda służąca do zwrócenia wszystkich historycznych wizyt posortowanych i po filtracji
     * @param visitPaginationDto - dane potrzebne do sortowania i filtracji wizyt
     * @param username - nazwa użytkownika
     * @return lista wizyt spaginowanych
     */
    @Override
    public VisitListResponse getAllHistoryVisitsPaginated(VisitPaginationDto visitPaginationDto, String username) {
        checkUserInfo(visitPaginationDto.getSearchingParams().getUserId(), username);

        HistoryVisitSpecification historyVisitSpecification =
                new HistoryVisitSpecification(visitPaginationDto.getSearchingParams());

        PageRequest visitPageRequest = getPageRequest(visitPaginationDto);
        Page<Visit> visitPage = visitRepository.findAll(historyVisitSpecification, visitPageRequest);
        log.info("Znaleziono historię wizyt");
        return findVisitListReponse(visitPage);
    }

    /**
     * Metoda służąca do zwrócenia wszystkich nadchodzących wizyt posortowanych i po filtracji
     * @param paginationDto - dane potrzebne do sortowania i filtracji wizyt
     * @param username - nazwa użytkownika
     * @return lista wizyt spaginowanych
     */
    @Override
    public VisitListResponse getAllUpcomingVisitsPaginated(VisitPaginationDto paginationDto, String username) {
        checkUserInfo(paginationDto.getSearchingParams().getUserId(), username);

        UpcomingVisitSpecification visitSpecification = new UpcomingVisitSpecification(paginationDto.getSearchingParams());

        PageRequest visitPageRequest = getPageRequest(paginationDto);
        Page<Visit> visitPage = visitRepository.findAll(visitSpecification, visitPageRequest);
        log.info("Znaleziono nadchodzące wizyty");
        return findVisitListReponse(visitPage);
    }

    /**
     * Metoda do wysyłania zaplanowanych wiadomości SMS codziennie o 7:00 z nadchodzącymi wizytami użytkownika (dzisiejszymi)
     */
    @Override
    @Scheduled(cron = "0 0 7 * * *")
    public void sendSMSReminder() {
        String sid = System.getenv("TWILIO_ACCOUNT_SID");
        String token = System.getenv("TWILIO_AUTH_TOKEN");
        Twilio.init(sid, token);


        LocalDateTime today = LocalDateTime.now();
        List<Visit> todayVisits = visitRepository.findByStartTimeIsGreaterThanAndStartTimeIsLessThanAndVisitStatusEnum(
                LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 0, 0),
                LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 23, 59), VisitStatusEnum.UPCOMING);

        for (Visit visit:todayVisits) {
            List<User> users = visit.getUsers();
            List<User> patient = users.stream().filter(c -> c.getDoctor() == null && c.getAssistant() == null).collect(Collectors.toList());
            Message.creator(new PhoneNumber(patient.get(0).getPhoneNumber()),
                    new PhoneNumber("+13467067816"), "Masz zaplanowaną wizytę dzisiejszego dnia. Data i godzina: " +
                    visit.getStartTime().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"))).create();
        }
        log.info("Wysłano powiadomienie SMS o wizytach");
    }

    /**
     * Metoda służąca do zwrócenia wszystkich wolnych wizyt posortowanych i po filtracji
     * @param paginationDto - dane potrzebne do sortowania i filtracji wizyt
     * @return lista wizyt spaginowanych
     */
    @Override
    public VisitListResponse getAllFreeVisitsPaginated(VisitPaginationDto paginationDto) {
        FreeVisitSpecification visitSpecification = new FreeVisitSpecification(paginationDto.getSearchingParams());

        PageRequest visitPageRequest = getPageRequest(paginationDto);
        Page<Visit> visitPage = visitRepository.findAll(visitSpecification, visitPageRequest);
        log.info("Znaleziono wolne terminy");
        return findVisitListReponse(visitPage);
    }

    /**
     * Metoda służąca do tworzenia paginacji wizyt
     * @param visitPage - strona wizyt
     * @return lista wizyt
     */
    private VisitListResponse findVisitListReponse(Page<Visit> visitPage) {
        List<VisitInfoDto> visitInfoDtos = visitPage.stream().map(visitMapper::visitToVisitInfoDto)
                .collect(Collectors.toList());
        log.info("Poprawne pobranie listy wizyt");

        Page<VisitInfoDto> visitInfoDtoPage = new PageImpl(visitInfoDtos, visitPage.getPageable(),
                visitPage.getTotalElements());
        return new VisitListResponse(visitInfoDtoPage);
    }

    /**
     * Metoda służaca do znajdowania wizyt w bazie danych po id
     * @param id - id wizyty (bazodanowe)
     * @return wizyta (Visit)
     */
    private Visit findVisitById(Long id) {
        Visit visitCheck = visitRepository.findById(id).orElse(null);
        if (visitCheck == null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.VISIT_NOT_FOUND);
        }
        return visitCheck;
    }

    /**
     * Metoda służąca do znalezienia czy istnieje dany użytkownik w bazie danych i czy podana nazwa użytkownika
     * zgadza się z bazodanową
     * @param userId - id użytkownika (bazodawnowe)
     * @param username - nazwa użytkownika
     * @return użytkownik (User)
     */
    private User checkUserInfo(Long userId, String username) {
        User userCheck = userRepository.findById(userId).orElse(null);
        User userCheckByUsername = userRepository.findByUserName(username).orElse(null);
        if (userCheck == null || userCheckByUsername == null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.USER_NOT_FOUND);
        }

        if (!Objects.equals(userCheckByUsername.getId(), userId) && userCheckByUsername.getDoctor() == null
                && userCheckByUsername.getAssistant() == null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.CANNOT_CREATE_OR_FIND_VISIT);
        }
        return userCheck;
    }

    /**
     * Metoda służąca do znalezienia użytkownika w bazie danych po id
     * @param userId - id użytkownika (bazodanowe)
     * @return
     */
    public User findUserById(Long userId) {
        User userCheck = userRepository.findById(userId).orElse(null);
        if (userCheck == null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.USER_NOT_FOUND);
        }
        return userCheck;
    }

    /**
     * Metoda służaca do znalezienia skierowań po id
     * @param refferalId - skierowanie id
     * @return skierowanie (Refferal)
     */
    public Refferal findRefferalById(Long refferalId) {
        Refferal refferal = null;
        if (refferalId != null) {
            refferal = refferalRepository.findById(refferalId).orElse(null);
            if (refferal == null) {
                throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.REFFERAL_NOT_FOUND);
            }
        }

        return refferal;
    }
}
