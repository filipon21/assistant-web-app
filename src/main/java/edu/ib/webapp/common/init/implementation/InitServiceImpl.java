package edu.ib.webapp.common.init.implementation;

import edu.ib.webapp.common.configuration.PasswordEncoderCustom;
import edu.ib.webapp.common.init.InitService;
import edu.ib.webapp.user.entity.*;
import edu.ib.webapp.user.enums.*;
import edu.ib.webapp.user.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional
public class InitServiceImpl implements InitService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final DoctorRepository doctorRepository;

    private final AssistantRepository assistantRepository;

    private final ExemptionRepository exemptionRepository;

    private final PrescriptionRepository prescriptionRepository;

    private final VisitRepository visitRepository;

    private final RefferalRepository refferalRepository;

    private final PasswordEncoderCustom passwordEncoderCustom;


    public Visit createVisit(String address, VisitTypeEnum visitTypeEnum, VisitStatusEnum visitStatusEnum,
                             LocalDateTime startTime, LocalDateTime endTime, String recommendation, User doctor, User user,
                             Exemption exemption, Prescription prescription, String description) {
        Visit visit = new Visit();
        visit.setAddress(address);
        visit.setVisitTypeEnum(visitTypeEnum);
        visit.setVisitStatusEnum(visitStatusEnum);
        visit.setStartTime(startTime);
        if (endTime != null) {
            visit.setEndTime(endTime);
        }
        visit.setRecommendation(recommendation);
        visit.setDescription(description);
        visitRepository.save(visit);
        visit.setExemption(exemption);
        visit.setPrescription(prescription);
        visitRepository.save(visit);
        return visit;
    }

    public Exemption createExemption(LocalDateTime startTime, LocalDateTime endTime) {
        Exemption exemption = new Exemption();
        exemption.setEndTime(endTime);
        exemption.setStartTime(startTime);
        exemptionRepository.save(exemption);
        return exemption;
    }

    public Prescription createPrescription(String type, String code, String fileCode) {
        Prescription prescription = new Prescription();
        prescription.setType(type);
        prescription.setCode(code);
        prescription.setFileCode(fileCode);
        prescriptionRepository.save(prescription);
        return prescription;
    }

    public Refferal createRefferal(Visit visit, RefferalStatusEnum refferalStatusEnum, LocalDateTime endTime,
                                   DoctorSpecializationEnum doctorSpecializationEnum) {
        Refferal refferal = new Refferal();
        refferal.setVisit(visit);
        refferal.setStatus(refferalStatusEnum);
        refferal.setEndTime(endTime);
        refferal.setDoctorSpecializationEnum(doctorSpecializationEnum);
        refferalRepository.save(refferal);
        return refferal;
    }

    @Override
    @Transactional
    @PostConstruct
    public void initRoleAndUser() {

        Role assistantRole = new Role();
        assistantRole.setRoleName("ASSISTANT");
        assistantRole.setRoleDescription("Rola dla asystenta pacjenta");
        roleRepository.save(assistantRole);

        Role userRole = new Role();
        userRole.setRoleName("USER");
        userRole.setRoleDescription("Domyślna rola dla nowo utworzonego użytkownika");
        roleRepository.save(userRole);

        Role doctorRole = new Role();
        doctorRole.setRoleName("DOCTOR");
        doctorRole.setRoleDescription("Rola dla lekarza");
        roleRepository.save(doctorRole);

        Doctor doctorCardio = new Doctor();
        doctorCardio.setDoctorSpecializationEnum(DoctorSpecializationEnum.CARDIOLOGIST);
        doctorRepository.save(doctorCardio);

        Doctor doctorInternist = new Doctor();
        doctorInternist.setDoctorSpecializationEnum(DoctorSpecializationEnum.NEUROLOGIST);
        doctorRepository.save(doctorInternist);

        Doctor doctor1 = new Doctor();
        doctor1.setDoctorSpecializationEnum(DoctorSpecializationEnum.INTERNIST);
        doctorRepository.save(doctor1);

        Assistant assistant1 = new Assistant();
        assistant1.setAssistantSpecializationEnum(AssistantSpecializationEnum.RECRUIT);
        assistantRepository.save(assistant1);

        Assistant assistant = new Assistant();
        assistant.setAssistantSpecializationEnum(AssistantSpecializationEnum.PRACTICED);
        assistantRepository.save(assistant);

        User assistantUser = new User();
        assistantUser.setUserName("assistant@gmail.com");
        assistantUser.setUserPassword(passwordEncoderCustom.getEncodedPassword("ass123!"));
        assistantUser.setUserFirstName("Filip");
        assistantUser.setUserLastName("Pomocny");
        assistantUser.setPesel("98060343412");
        assistantUser.setAddress("Balonowa 32");
        assistantUser.setCountry("Poland");
        assistantUser.setPostalCode("43-344");
        assistantUser.setVoivodeship("Dolnośląskie");
        assistantUser.setPhoneNumber("500455444");
        assistantUser.setIsOnline(true);
        assistantUser.setIsActive(true);
        Set<Role> assistantRoles = new HashSet<>();
        assistantRoles.add(assistantRole);
        assistantUser.setRoles(assistantRoles);
        assistantUser.setAssistant(assistant);
        userRepository.save(assistantUser);

        User assistantUser1 = new User();
        assistantUser1.setUserName("krej@gmail.com");
        assistantUser1.setUserPassword(passwordEncoderCustom.getEncodedPassword("ass123!"));
        assistantUser1.setUserFirstName("Adam");
        assistantUser1.setPesel("61050863553");
        assistantUser1.setAddress("Balonowa 32");
        assistantUser1.setCountry("Poland");
        assistantUser1.setPostalCode("43-344");
        assistantUser1.setVoivodeship("Dolnośląskie");
        assistantUser1.setPhoneNumber("500433444");
        assistantUser1.setIsOnline(false);
        assistantUser1.setIsActive(true);
        assistantUser1.setUserLastName("Krej");
        assistantUser1.setRoles(assistantRoles);

        assistantUser1.setAssistant(assistant1);
        userRepository.save(assistantUser1);

        User assistantUser2 = new User();
        assistantUser2.setUserName("as@gmail.com");
        assistantUser2.setUserPassword(passwordEncoderCustom.getEncodedPassword("ass123!"));
        assistantUser2.setUserFirstName("Michał");
        assistantUser2.setPesel("90080175385");
        assistantUser2.setPhoneNumber("500433433");
        assistantUser2.setIsOnline(false);
        assistantUser2.setIsActive(true);
        assistantUser2.setUserLastName("Bocz");
        assistantUser2.setRoles(assistantRoles);

        assistantUser2.setAssistant(assistant1);
        userRepository.save(assistantUser2);

        User assistantUser3 = new User();
        assistantUser3.setUserName("as1@gmail.com");
        assistantUser3.setUserPassword(passwordEncoderCustom.getEncodedPassword("ass123!"));
        assistantUser3.setUserFirstName("Grzegorz");
        assistantUser3.setPesel("58100828226");
        assistantUser3.setPhoneNumber("500433421");
        assistantUser3.setIsOnline(false);
        assistantUser3.setIsActive(true);
        assistantUser3.setUserLastName("Cel");
        assistantUser3.setRoles(assistantRoles);

        assistantUser3.setAssistant(assistant1);
        userRepository.save(assistantUser3);

        User doctor = new User();
        doctor.setUserName("doktor1@gmail.com");
        doctor.setUserPassword(passwordEncoderCustom.getEncodedPassword("raj123!"));
        doctor.setUserFirstName("Kamil");
        doctor.setUserLastName("Dobry");
        doctor.setIsOnline(true);
        doctor.setPesel("03242418879");
        doctor.setAddress("Balonowa 32");
        doctor.setCountry("Poland");
        doctor.setPostalCode("43-344");
        doctor.setVoivodeship("Dolnośląskie");
        doctor.setPhoneNumber("501455444");
        doctor.setIsActive(true);

        doctor.setDoctor(doctorCardio);
        Set<Role> doctorRoles = new HashSet<>();
        doctorRoles.add(doctorRole);
        doctor.setRoles(doctorRoles);
        userRepository.save(doctor);

        User doctorIn = new User();
        doctorIn.setUserName("doktorIn@gmail.com");
        doctorIn.setUserPassword(passwordEncoderCustom.getEncodedPassword("raj123!"));
        doctorIn.setUserFirstName("Adrian");
        doctorIn.setPesel("00000000000");
        doctorIn.setUserLastName("Medal");
        doctorIn.setIsOnline(true);
        doctorIn.setPhoneNumber("666666666");
        doctorIn.setIsActive(true);

        doctorIn.setDoctor(doctorInternist);
        Set<Role> doctorRoles5 = new HashSet<>();
        doctorRoles5.add(doctorRole);
        doctorIn.setRoles(doctorRoles);
        userRepository.save(doctorIn);

        User userDoctor1 = new User();
        userDoctor1.setUserName("doktor2@gmail.com");
        userDoctor1.setUserPassword(passwordEncoderCustom.getEncodedPassword("raj123!"));
        userDoctor1.setUserFirstName("Maciej");
        userDoctor1.setUserLastName("Adamski");
        userDoctor1.setPesel("51051757939");
        userDoctor1.setAddress("Balonowa 32");
        userDoctor1.setCountry("Poland");
        userDoctor1.setPostalCode("43-344");
        userDoctor1.setVoivodeship("Dolnośląskie");
        userDoctor1.setIsOnline(false);
        userDoctor1.setPhoneNumber("550455444");
        userDoctor1.setIsActive(true);

        userDoctor1.setDoctor(doctor1);
        userDoctor1.setRoles(doctorRoles);
        userRepository.save(userDoctor1);
//----------------------------------------------------------------
        User user = new User();
        user.setUserName("raj123@gmail.com");
        user.setUserPassword(passwordEncoderCustom.getEncodedPassword("raj123!"));
        user.setUserFirstName("Kamil");
        user.setUserLastName("Adam");
        user.setBirthday(LocalDate.of(2000,10,10));
        user.setAge(22L);
        user.setAddress("Balonowa 32");
        user.setCountry("Poland");
        user.setPostalCode("43-344");
        user.setVoivodeship("Dolnośląskie");
        user.setPhoneNumber("+48500346539");
        user.setIsOnline(true);
        user.setIsActive(true);
        user.setPesel("91091971131");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);
        List<Visit> userVisits = user.getVisits();
        userRepository.save(user);

        Exemption exemption = createExemption(LocalDateTime.now(), (LocalDateTime.now().plusYears(1)));
        Exemption exemption1 = createExemption(LocalDateTime.now(), (LocalDateTime.now().plusYears(1)));
        Exemption exemption2 = createExemption(LocalDateTime.now(), (LocalDateTime.now().plusYears(1)));

        Prescription prescription = createPrescription("application/pdf", "2323", "bb2UvSNM");
        Prescription prescription1 = createPrescription("application/pdf", "2323", "bb2UvSNM");
        Prescription prescription2 = createPrescription("application/pdf", "2323", "bb2UvSNM");

        Visit visit = createVisit("Brońskiego 32", VisitTypeEnum.STATIONARY, VisitStatusEnum.ENDED, LocalDateTime.now().minusMinutes(39), LocalDateTime.now(),
                "Należy obserwować zmiany na skórze.", assistantUser1, user, exemption, prescription, "Kod B7");

        Refferal refferal = createRefferal(visit,
                RefferalStatusEnum.ISSUED, LocalDateTime.now().plusYears(1), DoctorSpecializationEnum.CARDIOLOGIST);

        List<Visit> doctors = doctor.getVisits();
        doctors.add(visit);
        List<Visit> users = user.getVisits();
        users.add(visit);

        Visit visit1 = createVisit("Solna 23", VisitTypeEnum.STATIONARY, VisitStatusEnum.ENDED, LocalDateTime.now().minusMinutes(30), LocalDateTime.now(),
                "Smarowanie lekiem 3 razzy dziennie", assistantUser1, user, exemption1, prescription1, "Kod B8");
        Visit visit2 = createVisit("Solna 23", VisitTypeEnum.STATIONARY, VisitStatusEnum.UPCOMING, LocalDateTime.now().plusMinutes(100), null,
                "Nosić ortezę rok", assistantUser1, user, exemption2, prescription2, "Kod A7");

        List<Visit> assistants = assistantUser1.getVisits();
        assistants.add(visit1);
        users.add(visit1);

        assistants.add(visit2);
        users.add(visit2);

        userRepository.save(doctor);
        userRepository.save(assistantUser1);
        userRepository.save(user);

    }

}
