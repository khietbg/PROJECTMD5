package rikkei.academy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_Id")
    private Users users;
    @ManyToOne
    @JoinColumn(name = "friend")
    private Users friend;
    @Lob
    private String content;
    private LocalDate created;
    private boolean status;
}
