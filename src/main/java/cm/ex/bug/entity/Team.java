package cm.ex.bug.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "team")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    private String iconUrl;

    private String backgroundUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "leader_id", referencedColumnName = "id")
    private User leader;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "team__members",
            joinColumns = @JoinColumn(name = "team_id", updatable = true),
            inverseJoinColumns = @JoinColumn(name = "member_id", updatable = true))
    private Set<User> teamMembers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "team__invitation",
            joinColumns = @JoinColumn(name = "team_id", updatable = true),
            inverseJoinColumns = @JoinColumn(name = "member_id", updatable = true))
    private Set<User> teamInvitations = new HashSet<>();

}
