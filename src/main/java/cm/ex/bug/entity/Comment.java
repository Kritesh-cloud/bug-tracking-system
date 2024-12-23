package cm.ex.bug.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "comment")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String content;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "commenter_id", referencedColumnName = "id")
    private User commenter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    private Report report;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "comment__mentions",
            joinColumns = @JoinColumn(name = "comment_id", updatable = true),
            inverseJoinColumns = @JoinColumn(name = "user_id", updatable = true))
    private Set<User> userMentions = new HashSet<>();
}
