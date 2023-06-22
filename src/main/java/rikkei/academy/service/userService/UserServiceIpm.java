package rikkei.academy.service.userService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Users;
import rikkei.academy.repository.IUserRepository;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceIpm implements IUserService{
    private final IUserRepository userRepository;
    @Override
    public Iterable<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public Users save(Users users) {
        return userRepository.save(users);
    }


    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
