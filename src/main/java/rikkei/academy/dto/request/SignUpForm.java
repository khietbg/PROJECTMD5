package rikkei.academy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpForm {
    @Size(min = 10,message = "fullName from 10 character!")
    private String fullName;
    @Size(min = 6,message = "username from 6 character!")
    private String username;
    @Email(message = "email invalid!")
    private String email;
    @Size(min = 6,message = "password from 6 character!")
    private String password;
    Set<String> roles;

}
