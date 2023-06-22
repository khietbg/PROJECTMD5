package rikkei.academy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.SignInForm;
import rikkei.academy.dto.request.SignUpForm;
import rikkei.academy.dto.response.JwtResponse;
import rikkei.academy.dto.response.ResponseMessage;
import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;
import rikkei.academy.model.Users;
import rikkei.academy.service.sendEmail.SendEmailService;
import rikkei.academy.service.userService.IRoleService;
import rikkei.academy.service.userService.IUserService;
import rikkei.academy.security.jwt.JwtProvider;
import rikkei.academy.security.usersPrincipal.UserPrincipal;


import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;
    private final IRoleService roleService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final SendEmailService sendEmailService;

    @PostMapping("/signUp")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm, BindingResult bindingResult) throws MessagingException {
        if (bindingResult.hasErrors()){
            List<FieldError> list = bindingResult.getFieldErrors();
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError f:list) {
                stringBuilder.append(f.getField()).append(":").append(f.getDefaultMessage()).append(" : ");
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByUsername(signUpForm.getUsername())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponseMessage.builder()
                            .status("FAILED")
                            .message("This username is already existed!")
                            .data("")
                            .build()
            );
        }
        if (userService.existsByEmail(signUpForm.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ResponseMessage.builder()
                            .status("FAILED")
                            .message("This email is already existed!")
                            .data("")
                            .build()
            );
        }
        Set<Role> roles = new HashSet<>();
        if (signUpForm.getRoles() == null || signUpForm.getRoles().isEmpty()) {
            Role role = roleService.findByName(RoleName.USER)
                    .orElseThrow(() -> new RuntimeException("NOT FOUND ROLE"));
            roles.add(role);
        } else {
            signUpForm.getRoles().forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findByName(RoleName.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
                        roles.add(adminRole);
                    case "pm":
                        Role pmRole = roleService.findByName(RoleName.PM)
                                .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
                        roles.add(pmRole);
                    case "user":
                        Role userRole = roleService.findByName(RoleName.USER)
                                .orElseThrow(() -> new RuntimeException("Failed -> NOT FOUND ROLE"));
                        roles.add(userRole);
                }
            });
        }
        Users user = Users.builder()
                .fullName(signUpForm.getFullName())
                .username(signUpForm.getUsername())
                .email(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .roles(roles)
                .status(true)
                .build();
        Users users = userService.save(user);
        String html = "<i>welcome: </i>"+users.getUsername()+"<br>"+
                "<b>password: </b>"+ signUpForm.getPassword()+"<br>"+
                "<p>let's  good experience</p>";

        sendEmailService.sendMail(user.getEmail(),"register Successfully", html);
        return ResponseEntity.ok().body(
                ResponseMessage.builder()
                        .status("OK")
                        .message("Account created successfully!")
                        .data(users)
                        .build()
        );

    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignInForm signInForm) {
        Users users = userService.findByUsername(signInForm.getUsername()).get();
        if (!users.isStatus()) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .status("false")
                    .message("account locked, please contact to admin!")
                    .data("")
                    .build(), HttpStatus.NOT_FOUND);
        } else {


            try {
                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
                String token = jwtProvider.generateToken(authentication);
                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                return new ResponseEntity<>(
                        JwtResponse.builder()
                                .status("OK")
                                .type("Bearer")
                                .fullName(userPrincipal.getFullName())
                                .token(token)
                                .roles(userPrincipal.getAuthorities())
                                .build(), HttpStatus.OK);
            } catch (AuthenticationException e) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .message("username or account invalid")
                        .status("Failed")
                        .data("")
                        .build(), HttpStatus.UNAUTHORIZED);
            }
        }
    }
}
