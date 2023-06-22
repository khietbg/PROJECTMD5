package rikkei.academy.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    private String title;
    private String content;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate postDate;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_image",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    private List<Images> images;
    @OneToMany(mappedBy = "post",targetEntity = Comments.class)
    @JsonIgnoreProperties({"post"})
    private List<Comments> listComments;
    private boolean status;
}
