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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "leader_id", referencedColumnName = "id")
    private User leader;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "team__members",
            joinColumns = @JoinColumn(name = "team_id", updatable = true),
            inverseJoinColumns = @JoinColumn(name = "member_id", updatable = true))
    private Set<User> teamMembers = new HashSet<>();

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "team__invitation",
            joinColumns = @JoinColumn(name = "team_id", updatable = true),
            inverseJoinColumns = @JoinColumn(name = "member_id", updatable = true))
    private Set<User> teamInvitations = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Team(String name, String description, User leader) {
        this.name = name;
        this.description = description;
        this.leader = leader;
    }
}
