package rikkei.academy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.ChangeEmail;
import rikkei.academy.dto.request.ChangePass;
import rikkei.academy.dto.request.ChangeRole;
import rikkei.academy.dto.request.UpdateUser;
import rikkei.academy.dto.response.ResponseMessage;
import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;
import rikkei.academy.model.Users;
import rikkei.academy.service.userService.IRoleService;
import rikkei.academy.service.userService.UserServiceIpm;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceIpm userServiceIpm;
    private final PasswordEncoder passwordEncoder;
    private final IRoleService roleService;

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Users> findAll() {
        return (List<Users>) userServiceIpm.findAll();
    }

    @GetMapping("/profile/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<Optional<Users>> profile(@PathVariable("id") Long id) {
        Optional<Users> users = Optional.ofNullable(userServiceIpm.findById(id));
        if (users.isPresent()) {
            return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UpdateUser userUp) {
        Users user = userServiceIpm.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            user.setAge(userUp.getAge());
            user.setAddress(userUp.getAddress());
            user.setPhone(userUp.getPhone());
            user.setFullName(userUp.getFullName());
            userServiceIpm.save(user);
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/changePass/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> changePass(@PathVariable("id") Long id, @RequestBody ChangePass pass) {
        Users user = userServiceIpm.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (!passwordEncoder.matches(pass.getOldPass(), user.getPassword())) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .status("false")
                    .message("old password not match, please try again!")
                    .build(), HttpStatus.NOT_FOUND);
        } else {
            user.setPassword(passwordEncoder.encode(pass.getNewPass()));
            userServiceIpm.save(user);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @PutMapping("/changeEmail/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> changeEmail(@PathVariable("id") Long id, @RequestBody ChangeEmail email) {
        if (userServiceIpm.existsByEmail(email.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponseMessage.builder()
                            .status("FAILED")
                            .message("This email is already existed!")
                            .data("")
                            .build()
            );
        }
        Users user = userServiceIpm.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            user.setEmail(email.getEmail());
            userServiceIpm.save(user);
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/changeStatus/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id) {
        Users users = userServiceIpm.findById(id);
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (users.getRoles().size() == 3) {
            return new ResponseEntity<>(ResponseMessage.builder().status("false").message("can't block admin!").build(), HttpStatus.NOT_FOUND);
        } else {
            users.setStatus(!users.isStatus());
            userServiceIpm.save(users);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @PutMapping("/changeRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> changeRole(@RequestBody ChangeRole changeRole) {
        Users users = userServiceIpm.findById(changeRole.getId());
        if (users.getRoles().size() == 3) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .status("false")
                    .message("can't change admin!")
                    .build(), HttpStatus.NOT_FOUND);
        } else {
            Set<Role> listRole = new HashSet<>();
            if (changeRole.getRoles() == null || changeRole.getRoles().isEmpty()) {
                Role role = roleService.findByName(RoleName.USER)
                        .orElseThrow(() -> new RuntimeException("NOT FOUND ROLE"));
                listRole.add(role);
            } else {
                changeRole.getRoles().forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleService.findByName(RoleName.ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
                            listRole.add(adminRole);
                        case "pm":
                            Role pmRole = roleService.findByName(RoleName.PM)
                                    .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
                            listRole.add(pmRole);
                        case "user":
                            Role userRole = roleService.findByName(RoleName.USER)
                                    .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
                            listRole.add(userRole);
                    }
                });

            }
            users.setRoles(listRole);
            userServiceIpm.save(users);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
