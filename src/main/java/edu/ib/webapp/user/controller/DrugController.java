package edu.ib.webapp.user.controller;

import edu.ib.webapp.common.exception.ExceptionMessage;
import edu.ib.webapp.common.exception.UserException;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.model.request.DrugRequest;
import edu.ib.webapp.user.model.response.DrugResponse;
import edu.ib.webapp.user.repository.DrugRepository;
import edu.ib.webapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drug")
public class DrugController {

    private final DrugRepository drugRepository;

    private final UserRepository userRepository;

//    @GetMapping("/{userId}")
//    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
//    public DrugResponse addDrugToUser(@RequestBody @Validated DrugRequest drugRequest, @PathVariable Long userId){
//        User userCheck = findUserById(userId);
//
//
//    }

    public User findUserById(Long userId) {
        User userCheck = userRepository.findById(userId).orElse(null);
        if (userCheck == null) {
            throw new UserException(HttpStatus.FORBIDDEN, ExceptionMessage.USER_NOT_FOUND);
        }
        return userCheck;
    }
}
