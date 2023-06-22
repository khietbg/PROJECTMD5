package rikkei.academy.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    private boolean status;
    public Like() {
    }

    public Like(Long id, Users users, Post post, boolean status) {
        this.id = id;
        this.users = users;
        this.post = post;
        this.status = status;
    }
}
