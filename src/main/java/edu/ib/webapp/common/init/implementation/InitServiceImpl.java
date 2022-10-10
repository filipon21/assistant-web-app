package edu.ib.webapp.common.init.implementation;

import edu.ib.webapp.common.configuration.PasswordEncoderCustom;
import edu.ib.webapp.common.init.InitService;
import edu.ib.webapp.user.entity.Assistant;
import edu.ib.webapp.user.entity.Doctor;
import edu.ib.webapp.user.entity.Role;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.enums.AssistantSpecializationEnum;
import edu.ib.webapp.user.enums.DoctorSpecializationEnum;
import edu.ib.webapp.user.repository.RoleRepository;
import edu.ib.webapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
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

    private final PasswordEncoderCustom passwordEncoderCustom;

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
        Assistant assistant = new Assistant();
        assistant.setAssistantSpecializationEnum(AssistantSpecializationEnum.PRACTICED);
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
        Assistant assistant1 = new Assistant();
        assistant1.setAssistantSpecializationEnum(AssistantSpecializationEnum.RECRUIT);
        assistantUser1.setAssistant(assistant1);
        userRepository.save(assistantUser1);

        User user = new User();
        user.setUserName("raj123@gmail.com");
        user.setUserPassword(passwordEncoderCustom.getEncodedPassword("raj123!"));
        user.setUserFirstName("Kamil");
        user.setUserLastName("Adam");
        user.setAddress("Balonowa 32");
        user.setCountry("Poland");
        user.setPostalCode("43-344");
        user.setVoivodeship("Dolnośląskie");
        user.setPhoneNumber("400045544");
        user.setIsOnline(true);
        user.setIsActive(true);
        user.setPesel("91091971131");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);
        userRepository.save(user);

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
        Doctor userDoctor = new Doctor();
        userDoctor.setDoctorSpecializationEnum(DoctorSpecializationEnum.CARDIOLOGIST);
        doctor.setDoctor(userDoctor);
        Set<Role> doctorRoles = new HashSet<>();
        doctorRoles.add(doctorRole);
        doctor.setRoles(doctorRoles);
        userRepository.save(doctor);

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
        Doctor doctor1 = new Doctor();
        doctor1.setDoctorSpecializationEnum(DoctorSpecializationEnum.INTERNIST);
        userDoctor1.setDoctor(doctor1);
        userDoctor1.setRoles(doctorRoles);
        userRepository.save(userDoctor1);

    }

}
