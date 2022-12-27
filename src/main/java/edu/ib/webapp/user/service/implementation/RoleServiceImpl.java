package edu.ib.webapp.user.service.implementation;

import edu.ib.webapp.user.entity.Role;
import edu.ib.webapp.user.repository.RoleRepository;
import edu.ib.webapp.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Klasa służąca do przetworzenia logiki biznesowej związanej z rolami (serwis Springowy)
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    /**
     * Metoda służąca do stworzenia nowej roli
     * @param role - dane roli
     * @return dane roli
     */
    @Transactional
    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }
}
