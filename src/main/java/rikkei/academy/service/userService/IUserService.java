package rikkei.academy.service.userService;

import org.springframework.security.core.userdetails.User;
import rikkei.academy.model.Users;
import rikkei.academy.service.IGenericService;

import java.util.Optional;

public interface IUserService extends IGenericService<Users,Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Users> findByUsername(String username);
}
