package edu.ib.webapp.user.service.implementation;

import edu.ib.webapp.common.exception.ExceptionMessage;
import edu.ib.webapp.common.exception.UserException;
import edu.ib.webapp.user.entity.Televisit;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.enums.TelevisitStatusEnum;
import edu.ib.webapp.user.enums.TelevisitTypeEnum;
import edu.ib.webapp.user.mapper.TelevisitMapper;
import edu.ib.webapp.user.model.dto.TelevisitInfoDto;
import edu.ib.webapp.user.model.dto.TelevisitPaginationDto;
import edu.ib.webapp.user.model.request.TelevisitRequest;
import edu.ib.webapp.user.model.response.TelevisitListResponse;
import edu.ib.webapp.user.model.response.TelevisitResponse;
import edu.ib.webapp.user.repository.UserRepository;
import edu.ib.webapp.user.repository.TelevisitRepository;
import edu.ib.webapp.user.repository.specification.TelevisitSpecification;
import edu.ib.webapp.user.service.TelevisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static edu.ib.webapp.user.pagination.TelevisitPaginationSupport.getPageRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelevisitServiceImpl implements TelevisitService {

    private final TelevisitRepository televisitRepository;

    private final TelevisitMapper televisitMapper;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public TelevisitResponse createVisit(TelevisitRequest televisitRequest, Long userId, Long assistantId, String username) {
        User userCheck = userRepository.findById(userId).orElse(null);
        User userCheckByUsername = userRepository.findByUserName(username).orElse(null);
        if (userCheck == null || userCheckByUsername == null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.USER_NOT_FOUND);
        }

        User assistantCheck = userRepository.findById(assistantId).orElse(null);

        if (assistantCheck.getAssistant() == null){
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.ASSISTANT_NOT_FOUND);
        }

        List<Televisit> visitsCheck = televisitRepository.findByUsers_idAndTelevisitStatusEnum(userId, TelevisitStatusEnum.WAITING);
        List<Televisit> visitsCheck2 = televisitRepository.findByUsers_idAndTelevisitStatusEnum(userId, TelevisitStatusEnum.STARTED);
        if (visitsCheck.size() != 0 || visitsCheck2.size() != 0){
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.CANNOT_CREATE_NEW_VISIT);
        }

        if (!Objects.equals(userCheckByUsername.getId(), userId)){
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.CANNOT_CREATE_VISIT);
        }

        Televisit televisit = televisitMapper.visitRequestToVisit(televisitRequest);

        List<Televisit> televisits = userCheck.getTelevisits();
        televisits.add(televisit);

        List<Televisit> assistantTelevisits = assistantCheck.getTelevisits();
        assistantTelevisits.add(televisit);

        televisit.setStartTime(LocalDateTime.now());

        televisit.setTelevisitStatusEnum(TelevisitStatusEnum.WAITING);

        TelevisitResponse televisitResponse = televisitMapper.visitToVisitResponse(televisitRepository.save(televisit));

        log.info(String.format("[Visit id: %s][Visit type: %s] Successful created new visit",
                televisit.getId(),
                televisit.getTelevisitTypeEnum())
        );

        return televisitResponse;
    }

    @Override
    @Transactional
    public TelevisitResponse updateVisit(Long id, TelevisitRequest televisitRequest) {
        Televisit televisitCheck = findVisitById(id);
        TelevisitStatusEnum televisitStatusEnum = televisitRequest.getTelevisitStatusEnum();
        TelevisitTypeEnum televisitTypeEnum = televisitRequest.getTelevisitTypeEnum();
        if (televisitCheck == null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.VISIT_NOT_FOUND);
        }
        if (televisitStatusEnum != null){
            televisitCheck.setTelevisitStatusEnum(televisitRequest.getTelevisitStatusEnum());
        }
        if (televisitTypeEnum != null){
            televisitCheck.setTelevisitTypeEnum(televisitRequest.getTelevisitTypeEnum());
        }
        televisitRepository.save(televisitCheck);

        return televisitMapper.visitToVisitResponse(televisitCheck);
    }

    @Override
    public List<TelevisitResponse> getUserVisits(Long id) {
        List<Televisit> televisits = televisitRepository.findByUsers_idAndTelevisitStatusEnum(id, TelevisitStatusEnum.WAITING);
        System.out.println(televisits);
        List<TelevisitResponse> televisitResponse = televisits.stream().map(televisitMapper::visitToVisitResponse)
                .collect(Collectors.toList());
        return televisitResponse;
    }

    @Override
    public TelevisitResponse getVisit(Long id) {
        Televisit televisit = findVisitById(id);
        if (televisit == null){
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.VISIT_NOT_FOUND);
        }
        return televisitMapper.visitToVisitResponse(televisit);
    }

    @Override
    public TelevisitListResponse getAllTelevisitsPaginated(TelevisitPaginationDto televisitPaginationDto) {
        TelevisitSpecification televisitSpecification = new TelevisitSpecification(televisitPaginationDto.getSearchingParams());

        PageRequest televisitPageRequest = getPageRequest(televisitPaginationDto);
        Page<Televisit> televisitPage = televisitRepository.findAll(televisitSpecification, televisitPageRequest);

        List<TelevisitInfoDto> televisitInfoDtos = televisitPage.stream().map(televisitMapper::televisitToTelevisitInfoDto)
                .collect(Collectors.toList());
        log.info("Poprawne pobranie listy telewizyt");

        Page<TelevisitInfoDto> televisitInfoDtoPage = new PageImpl(televisitInfoDtos, televisitPage.getPageable(),
                televisitPage.getTotalElements());
        return new TelevisitListResponse(televisitInfoDtoPage);
    }

    public Televisit findVisitById(Long id){
        return televisitRepository.findById(id).orElse(null);
    }
}
