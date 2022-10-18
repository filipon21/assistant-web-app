package edu.ib.webapp.user.service.implementation;

import edu.ib.webapp.common.exception.ExceptionMessage;
import edu.ib.webapp.common.exception.UserException;
import edu.ib.webapp.user.entity.Visit;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.enums.VisitStatusEnum;
import edu.ib.webapp.user.enums.VisitTypeEnum;
import edu.ib.webapp.user.mapper.VisitMapper;
import edu.ib.webapp.user.model.dto.VisitInfoDto;
import edu.ib.webapp.user.model.dto.VisitPaginationDto;
import edu.ib.webapp.user.model.request.VisitRequest;
import edu.ib.webapp.user.model.response.VisitListResponse;
import edu.ib.webapp.user.model.response.VisitResponse;
import edu.ib.webapp.user.repository.UserRepository;
import edu.ib.webapp.user.repository.VisitRepository;
import edu.ib.webapp.user.repository.specification.VisitSpecification;
import edu.ib.webapp.user.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static edu.ib.webapp.user.pagination.VisitPaginationSupport.getPageRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    private final VisitMapper visitMapper;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public VisitResponse createVisit(VisitRequest visitRequest, Long userId, Long assistantId, String username) {
        User userCheck = userRepository.findById(userId).orElse(null);
        User userCheckByUsername = userRepository.findByUserName(username).orElse(null);
        if (userCheck == null || userCheckByUsername == null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.USER_NOT_FOUND);
        }

        User assistantCheck = userRepository.findById(assistantId).orElse(null);

        if (assistantCheck.getAssistant() == null){
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.ASSISTANT_NOT_FOUND);
        }

        Visit visitsCheck = visitRepository.findFirstByUsers_IdAndVisitStatusEnumOrVisitStatusEnumOrVisitStatusEnumOrderByStartTimeDesc
                (userId, VisitStatusEnum.WAITING, VisitStatusEnum.UPCOMING, VisitStatusEnum.STARTED);

        if (visitsCheck != null){
            long minutes = ChronoUnit.MINUTES.between(visitsCheck.getStartTime(), LocalDateTime.now());
            if (minutes<30){
                throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.CANNOT_CREATE_NEW_VISIT);
            }
        }

        if (!Objects.equals(userCheckByUsername.getId(), userId)){
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.CANNOT_CREATE_VISIT);
        }

        Visit visit = visitMapper.visitRequestToVisit(visitRequest);

        List<Visit> visits = userCheck.getVisits();
        visits.add(visit);

        List<Visit> assistantVisits = assistantCheck.getVisits();
        assistantVisits.add(visit);

        visit.setStartTime(LocalDateTime.now());

        visit.setVisitStatusEnum(VisitStatusEnum.WAITING);

        VisitResponse visitResponse = visitMapper.visitToVisitResponse(visitRepository.save(visit));
        
        log.info(String.format("[Visit id: %s][Visit type: %s] Successful created new visit",
                visit.getId(),
                visit.getVisitTypeEnum())
        );

        return visitResponse;
    }

    @Override
    @Transactional
    public VisitResponse updateVisit(Long id, VisitRequest visitRequest) {
        Visit visitCheck = findVisitById(id);
        VisitStatusEnum visitStatusEnum = visitRequest.getVisitStatusEnum();
        VisitTypeEnum visitTypeEnum = visitRequest.getVisitTypeEnum();
        if (visitCheck == null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.VISIT_NOT_FOUND);
        }
        if (visitStatusEnum != null){
            visitCheck.setVisitStatusEnum(visitRequest.getVisitStatusEnum());
        }
        if (visitTypeEnum != null){
            visitCheck.setVisitTypeEnum(visitRequest.getVisitTypeEnum());
        }
        visitRepository.save(visitCheck);

        return visitMapper.visitToVisitResponse(visitCheck);
    }

    @Override
    public VisitResponse getUserTeleVisit(Long id) {
        Visit visit = visitRepository.
                findFirstByUsers_IdAndVisitStatusEnumAndVisitTypeEnumIsNotOrVisitTypeEnumIsNotAndVisitStatusEnumOrderByStartTimeDesc(
                 id, VisitStatusEnum.STARTED, VisitTypeEnum.STATIONARY, VisitTypeEnum.STATIONARY, VisitStatusEnum.WAITING);
//        Visit visit = visitRepository.
//                findFirstByUsers_IdAndVisitTypeEnumIsNotOrderByStartTimeDesc(
//                        id, VisitTypeEnum.STATIONARY);
        System.out.println(visit);
        return visitMapper.visitToVisitResponse(visit);
    }

    @Override
    public VisitResponse getVisit(Long id) {
        Visit visit = findVisitById(id);
        if (visit == null){
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.VISIT_NOT_FOUND);
        }
        return visitMapper.visitToVisitResponse(visit);
    }

    @Override
    public VisitListResponse getAllTelevisitsPaginated(VisitPaginationDto visitPaginationDto) {
        VisitSpecification visitSpecification = new VisitSpecification(visitPaginationDto.getSearchingParams());

        PageRequest televisitPageRequest = getPageRequest(visitPaginationDto);
        Page<Visit> televisitPage = visitRepository.findAll(visitSpecification, televisitPageRequest);

        List<VisitInfoDto> visitInfoDtos = televisitPage.stream().map(visitMapper::televisitToTelevisitInfoDto)
                .collect(Collectors.toList());
        log.info("Poprawne pobranie listy telewizyt");

        Page<VisitInfoDto> televisitInfoDtoPage = new PageImpl(visitInfoDtos, televisitPage.getPageable(),
                televisitPage.getTotalElements());
        return new VisitListResponse(televisitInfoDtoPage);
    }

    public Visit findVisitById(Long id){
        return visitRepository.findById(id).orElse(null);
    }
}
