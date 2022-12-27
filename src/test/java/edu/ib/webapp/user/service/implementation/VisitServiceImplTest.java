package edu.ib.webapp.user.service.implementation;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.ib.webapp.common.exception.ExceptionMessage;
import edu.ib.webapp.common.exception.UserException;
import edu.ib.webapp.user.entity.Assistant;
import edu.ib.webapp.user.entity.Doctor;
import edu.ib.webapp.user.entity.Exemption;
import edu.ib.webapp.user.entity.Prescription;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.entity.Visit;
import edu.ib.webapp.user.enums.AssistantSpecializationEnum;
import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import edu.ib.webapp.user.enums.VisitStatusEnum;
import edu.ib.webapp.user.enums.VisitTypeEnum;
import edu.ib.webapp.user.mapper.VisitMapper;
import edu.ib.webapp.user.model.request.VisitRequest;
import edu.ib.webapp.user.model.response.VisitResponse;
import edu.ib.webapp.user.repository.RefferalRepository;
import edu.ib.webapp.user.repository.UserRepository;
import edu.ib.webapp.user.repository.VisitRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VisitServiceImpl.class})
@ExtendWith(SpringExtension.class)
class VisitServiceImplTest {
    @MockBean
    private RefferalRepository refferalRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private VisitMapper visitMapper;

    @MockBean
    private VisitRepository visitRepository;

    @Autowired
    private VisitServiceImpl visitServiceImpl;

    /**
     * Method under test: {@link VisitServiceImpl#updateVisit(Long, VisitRequest)}
     */
    @Test
    void testUpdateVisit() {
        Exemption exemption = new Exemption();
        exemption.setEndTime(null);
        exemption.setId(123L);
        exemption.setStartTime(null);
        exemption.setVisit(new Visit());

        Prescription prescription = new Prescription();
        prescription.setCode("Code");
        prescription.setFileCode("File Code");
        prescription.setId(123L);
        prescription.setType("Type");
        prescription.setVisit(new Visit());

        Visit visit = new Visit();
        visit.setAddress("42 Main St");
        visit.setChatLink("Chat Link");
        visit.setDescription("The characteristics of someone or something");
        visit.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit.setExemption(exemption);
        visit.setId(123L);
        visit.setPrescription(prescription);
        visit.setRecommendation("Recommendation");
        visit.setRefferalId(123L);
        visit.setRefferals(new ArrayList<>());
        visit.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit.setUsers(new ArrayList<>());
        visit.setVisitStatusEnum(VisitStatusEnum.WAITING);
        visit.setVisitTypeEnum(VisitTypeEnum.PHONE);

        Exemption exemption1 = new Exemption();
        exemption1.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        exemption1.setId(123L);
        exemption1.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        exemption1.setVisit(visit);

        Exemption exemption2 = new Exemption();
        exemption2.setEndTime(null);
        exemption2.setId(123L);
        exemption2.setStartTime(null);
        exemption2.setVisit(new Visit());

        Prescription prescription1 = new Prescription();
        prescription1.setCode("Code");
        prescription1.setFileCode("File Code");
        prescription1.setId(123L);
        prescription1.setType("Type");
        prescription1.setVisit(new Visit());

        Visit visit1 = new Visit();
        visit1.setAddress("42 Main St");
        visit1.setChatLink("Chat Link");
        visit1.setDescription("The characteristics of someone or something");
        visit1.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit1.setExemption(exemption2);
        visit1.setId(123L);
        visit1.setPrescription(prescription1);
        visit1.setRecommendation("Recommendation");
        visit1.setRefferalId(123L);
        visit1.setRefferals(new ArrayList<>());
        visit1.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit1.setUsers(new ArrayList<>());
        visit1.setVisitStatusEnum(VisitStatusEnum.WAITING);
        visit1.setVisitTypeEnum(VisitTypeEnum.PHONE);

        Prescription prescription2 = new Prescription();
        prescription2.setCode("Code");
        prescription2.setFileCode("File Code");
        prescription2.setId(123L);
        prescription2.setType("Type");
        prescription2.setVisit(visit1);

        Visit visit2 = new Visit();
        visit2.setAddress("42 Main St");
        visit2.setChatLink("Chat Link");
        visit2.setDescription("The characteristics of someone or something");
        visit2.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit2.setExemption(exemption1);
        visit2.setId(123L);
        visit2.setPrescription(prescription2);
        visit2.setRecommendation("Recommendation");
        visit2.setRefferalId(123L);
        visit2.setRefferals(new ArrayList<>());
        visit2.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit2.setUsers(new ArrayList<>());
        visit2.setVisitStatusEnum(VisitStatusEnum.WAITING);
        visit2.setVisitTypeEnum(VisitTypeEnum.PHONE);
        Optional<Visit> ofResult = Optional.of(visit2);
        when(visitRepository.save((Visit) any()))
                .thenThrow(new UserException(HttpStatus.CONTINUE, ExceptionMessage.USER_NOT_FOUND));
        when(visitRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(UserException.class, () -> visitServiceImpl.updateVisit(123L, new VisitRequest()));
        verify(visitRepository).save((Visit) any());
        verify(visitRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link VisitServiceImpl#updateVisit(Long, VisitRequest)}
     */
    @Test
    void testUpdateVisit2() {
        Exemption exemption = new Exemption();
        exemption.setEndTime(null);
        exemption.setId(123L);
        exemption.setStartTime(null);
        exemption.setVisit(new Visit());

        Prescription prescription = new Prescription();
        prescription.setCode("Code");
        prescription.setFileCode("File Code");
        prescription.setId(123L);
        prescription.setType("Type");
        prescription.setVisit(new Visit());

        Visit visit = new Visit();
        visit.setAddress("42 Main St");
        visit.setChatLink("Chat Link");
        visit.setDescription("The characteristics of someone or something");
        visit.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit.setExemption(exemption);
        visit.setId(123L);
        visit.setPrescription(prescription);
        visit.setRecommendation("Recommendation");
        visit.setRefferalId(123L);
        visit.setRefferals(new ArrayList<>());
        visit.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit.setUsers(new ArrayList<>());
        visit.setVisitStatusEnum(VisitStatusEnum.WAITING);
        visit.setVisitTypeEnum(VisitTypeEnum.PHONE);

        Exemption exemption1 = new Exemption();
        exemption1.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        exemption1.setId(123L);
        exemption1.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        exemption1.setVisit(visit);

        Exemption exemption2 = new Exemption();
        exemption2.setEndTime(null);
        exemption2.setId(123L);
        exemption2.setStartTime(null);
        exemption2.setVisit(new Visit());

        Prescription prescription1 = new Prescription();
        prescription1.setCode("Code");
        prescription1.setFileCode("File Code");
        prescription1.setId(123L);
        prescription1.setType("Type");
        prescription1.setVisit(new Visit());

        Visit visit1 = new Visit();
        visit1.setAddress("42 Main St");
        visit1.setChatLink("Chat Link");
        visit1.setDescription("The characteristics of someone or something");
        visit1.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit1.setExemption(exemption2);
        visit1.setId(123L);
        visit1.setPrescription(prescription1);
        visit1.setRecommendation("Recommendation");
        visit1.setRefferalId(123L);
        visit1.setRefferals(new ArrayList<>());
        visit1.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit1.setUsers(new ArrayList<>());
        visit1.setVisitStatusEnum(VisitStatusEnum.WAITING);
        visit1.setVisitTypeEnum(VisitTypeEnum.PHONE);

        Prescription prescription2 = new Prescription();
        prescription2.setCode("Code");
        prescription2.setFileCode("File Code");
        prescription2.setId(123L);
        prescription2.setType("Type");
        prescription2.setVisit(visit1);

        Visit visit2 = new Visit();
        visit2.setAddress("42 Main St");
        visit2.setChatLink("Chat Link");
        visit2.setDescription("The characteristics of someone or something");
        visit2.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit2.setExemption(exemption1);
        visit2.setId(123L);
        visit2.setPrescription(prescription2);
        visit2.setRecommendation("Recommendation");
        visit2.setRefferalId(123L);
        visit2.setRefferals(new ArrayList<>());
        visit2.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit2.setUsers(new ArrayList<>());
        visit2.setVisitStatusEnum(VisitStatusEnum.WAITING);
        visit2.setVisitTypeEnum(VisitTypeEnum.PHONE);
        Optional<Visit> ofResult = Optional.of(visit2);

        Visit visit3 = new Visit();
        visit3.setAddress("42 Main St");
        visit3.setChatLink("Chat Link");
        visit3.setDescription("The characteristics of someone or something");
        visit3.setEndTime(null);
        visit3.setExemption(new Exemption());
        visit3.setId(123L);
        visit3.setPrescription(new Prescription());
        visit3.setRecommendation("Recommendation");
        visit3.setRefferalId(123L);
        visit3.setRefferals(new ArrayList<>());
        visit3.setStartTime(null);
        visit3.setUsers(new ArrayList<>());
        visit3.setVisitStatusEnum(VisitStatusEnum.WAITING);
        visit3.setVisitTypeEnum(VisitTypeEnum.PHONE);

        Exemption exemption3 = new Exemption();
        exemption3.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        exemption3.setId(123L);
        exemption3.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        exemption3.setVisit(visit3);

        Visit visit4 = new Visit();
        visit4.setAddress("42 Main St");
        visit4.setChatLink("Chat Link");
        visit4.setDescription("The characteristics of someone or something");
        visit4.setEndTime(null);
        visit4.setExemption(new Exemption());
        visit4.setId(123L);
        visit4.setPrescription(new Prescription());
        visit4.setRecommendation("Recommendation");
        visit4.setRefferalId(123L);
        visit4.setRefferals(new ArrayList<>());
        visit4.setStartTime(null);
        visit4.setUsers(new ArrayList<>());
        visit4.setVisitStatusEnum(VisitStatusEnum.WAITING);
        visit4.setVisitTypeEnum(VisitTypeEnum.PHONE);

        Prescription prescription3 = new Prescription();
        prescription3.setCode("Code");
        prescription3.setFileCode("File Code");
        prescription3.setId(123L);
        prescription3.setType("Type");
        prescription3.setVisit(visit4);

        Visit visit5 = new Visit();
        visit5.setAddress("42 Main St");
        visit5.setChatLink("Chat Link");
        visit5.setDescription("The characteristics of someone or something");
        visit5.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit5.setExemption(exemption3);
        visit5.setId(123L);
        visit5.setPrescription(prescription3);
        visit5.setRecommendation("Recommendation");
        visit5.setRefferalId(123L);
        visit5.setRefferals(new ArrayList<>());
        visit5.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit5.setUsers(new ArrayList<>());
        visit5.setVisitStatusEnum(VisitStatusEnum.WAITING);
        visit5.setVisitTypeEnum(VisitTypeEnum.PHONE);

        Exemption exemption4 = new Exemption();
        exemption4.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        exemption4.setId(123L);
        exemption4.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        exemption4.setVisit(visit5);

        Visit visit6 = new Visit();
        visit6.setAddress("42 Main St");
        visit6.setChatLink("Chat Link");
        visit6.setDescription("The characteristics of someone or something");
        visit6.setEndTime(null);
        visit6.setExemption(new Exemption());
        visit6.setId(123L);
        visit6.setPrescription(new Prescription());
        visit6.setRecommendation("Recommendation");
        visit6.setRefferalId(123L);
        visit6.setRefferals(new ArrayList<>());
        visit6.setStartTime(null);
        visit6.setUsers(new ArrayList<>());
        visit6.setVisitStatusEnum(VisitStatusEnum.WAITING);
        visit6.setVisitTypeEnum(VisitTypeEnum.PHONE);

        Exemption exemption5 = new Exemption();
        exemption5.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        exemption5.setId(123L);
        exemption5.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        exemption5.setVisit(visit6);

        Visit visit7 = new Visit();
        visit7.setAddress("42 Main St");
        visit7.setChatLink("Chat Link");
        visit7.setDescription("The characteristics of someone or something");
        visit7.setEndTime(null);
        visit7.setExemption(new Exemption());
        visit7.setId(123L);
        visit7.setPrescription(new Prescription());
        visit7.setRecommendation("Recommendation");
        visit7.setRefferalId(123L);
        visit7.setRefferals(new ArrayList<>());
        visit7.setStartTime(null);
        visit7.setUsers(new ArrayList<>());
        visit7.setVisitStatusEnum(VisitStatusEnum.WAITING);
        visit7.setVisitTypeEnum(VisitTypeEnum.PHONE);

        Prescription prescription4 = new Prescription();
        prescription4.setCode("Code");
        prescription4.setFileCode("File Code");
        prescription4.setId(123L);
        prescription4.setType("Type");
        prescription4.setVisit(visit7);

        Visit visit8 = new Visit();
        visit8.setAddress("42 Main St");
        visit8.setChatLink("Chat Link");
        visit8.setDescription("The characteristics of someone or something");
        visit8.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit8.setExemption(exemption5);
        visit8.setId(123L);
        visit8.setPrescription(prescription4);
        visit8.setRecommendation("Recommendation");
        visit8.setRefferalId(123L);
        visit8.setRefferals(new ArrayList<>());
        visit8.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit8.setUsers(new ArrayList<>());
        visit8.setVisitStatusEnum(VisitStatusEnum.WAITING);
        visit8.setVisitTypeEnum(VisitTypeEnum.PHONE);

        Prescription prescription5 = new Prescription();
        prescription5.setCode("Code");
        prescription5.setFileCode("File Code");
        prescription5.setId(123L);
        prescription5.setType("Type");
        prescription5.setVisit(visit8);

        Visit visit9 = new Visit();
        visit9.setAddress("42 Main St");
        visit9.setChatLink("Chat Link");
        visit9.setDescription("The characteristics of someone or something");
        visit9.setEndTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit9.setExemption(exemption4);
        visit9.setId(123L);
        visit9.setPrescription(prescription5);
        visit9.setRecommendation("Recommendation");
        visit9.setRefferalId(123L);
        visit9.setRefferals(new ArrayList<>());
        visit9.setStartTime(LocalDateTime.of(1, 1, 1, 1, 1));
        visit9.setUsers(new ArrayList<>());
        visit9.setVisitStatusEnum(VisitStatusEnum.WAITING);
        visit9.setVisitTypeEnum(VisitTypeEnum.PHONE);
        when(visitRepository.save((Visit) any())).thenReturn(visit9);
        when(visitRepository.findById((Long) any())).thenReturn(ofResult);
        VisitResponse visitResponse = new VisitResponse();
        when(visitMapper.visitToVisitResponse((Visit) any())).thenReturn(visitResponse);
        assertSame(visitResponse, visitServiceImpl.updateVisit(123L, new VisitRequest()));
        verify(visitRepository).save((Visit) any());
        verify(visitRepository).findById((Long) any());
        verify(visitMapper).visitToVisitResponse((Visit) any());
    }

}

