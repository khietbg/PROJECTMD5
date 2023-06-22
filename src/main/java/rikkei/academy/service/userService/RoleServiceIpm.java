package rikkei.academy.service.userService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;
import rikkei.academy.repository.IRoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceIpm implements IRoleService {
    private final IRoleRepository repository;

    @Override
    public Iterable<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Role> findByName(RoleName name) {
        return repository.findByName(name);
    }
}
