package rikkei.academy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 10,message = "fullName from 10 character!")
    private String fullName;
    @Size(min = 6,message = "username from 6 character!")
    @Column(unique = true)
    private String username;
    @Email(message = "email invalid!")
    @Column(unique = true)
    private String email;
    @Size(min = 6,message = "password from 6 character!")
    @JsonIgnore
    private String password;
    private int age;
    private String address;
    private String phone;
    private boolean status;
    @OneToMany(mappedBy = "friend",targetEntity = Friends.class,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Friends> friendsList;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @Override
    public String toString() {
        return new StringJoiner(", ", Users.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("fullName='" + fullName + "'")
                .add("username='" + username + "'")
                .add("email='" + email + "'")
                .add("password='" + password + "'")
                .add("age=" + age)
                .add("address='" + address + "'")
                .add("phone='" + phone + "'")
                .add("status=" + status)
                .add("friendsList=" + friendsList)
                .add("roles=" + roles)
                .toString();
    }
}
