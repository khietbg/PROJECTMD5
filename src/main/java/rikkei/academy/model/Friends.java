package rikkei.academy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.util.converter.LocalDateStringConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.StringJoiner;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "friends")
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @ManyToOne
    @JoinColumn(name = "friend_id")
    @JsonIgnoreProperties({"roles"})
    private Users friend;
    private LocalDate createdDate;
    private boolean status;

    @Override
    public String toString() {
        return new StringJoiner(", ", Friends.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("users=" + users)
                .add("friend=" + friend)
                .add("createdDate=" + createdDate)
                .add("status=" + status)
                .toString();
    }
}
