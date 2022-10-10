package edu.ib.webapp.api.service.implementation;

import edu.ib.webapp.api.model.dto.AssistantInfoDto;
import edu.ib.webapp.api.model.dto.AssistantPaginationDto;
import edu.ib.webapp.api.model.response.AssistantListResponse;
import edu.ib.webapp.api.repository.specification.AssistantSpecification;
import edu.ib.webapp.api.service.ApiService;
import edu.ib.webapp.user.entity.User;
import edu.ib.webapp.user.mapper.UserMapper;
import edu.ib.webapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static edu.ib.webapp.api.pagination.PaginationSupport.getPageRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public AssistantListResponse getAllPaginated(AssistantPaginationDto assistantPaginationDto) {
        AssistantSpecification assistantSpecification = new AssistantSpecification(assistantPaginationDto.getSearchingParams());

        PageRequest assistantPageRequest = getPageRequest(assistantPaginationDto);
        Page<User> assistantPage = userRepository.findAll(assistantSpecification, assistantPageRequest);

        List<AssistantInfoDto> assistantInfoDtos = assistantPage.stream().map(userMapper::userToAssistantInfoDto)
                .collect(Collectors.toList());

        log.info("Poprawne pobranie listy asystentów");

        Page<AssistantInfoDto> assistantInfoDtoPage = new PageImpl(assistantInfoDtos, assistantPage.getPageable(),
                assistantPage.getTotalElements());
        System.out.println(assistantInfoDtoPage);
        return new AssistantListResponse(assistantInfoDtoPage);
    }
}
