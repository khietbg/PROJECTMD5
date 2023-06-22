package rikkei.academy.security.usersPrincipal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import rikkei.academy.model.Users;
import rikkei.academy.service.userService.IUserService;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userService.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("failed-> USER NOT FOUND: "+username));
        return UserPrincipal.build(users);
    }
    public Users getUserLogin(){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findById(userPrincipal.getId());
    }
}
