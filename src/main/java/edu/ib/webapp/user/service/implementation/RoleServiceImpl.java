package edu.ib.webapp.user.service.implementation;

import edu.ib.webapp.user.repository.RoleRepository;
import edu.ib.webapp.user.entity.Role;
import edu.ib.webapp.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }
}
