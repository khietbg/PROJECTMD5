package rikkei.academy.service.userService;

import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;
import rikkei.academy.service.IGenericService;

import java.util.Optional;

public interface IRoleService extends IGenericService<Role,Long> {
    Optional<Role> findByName(RoleName name);
}
