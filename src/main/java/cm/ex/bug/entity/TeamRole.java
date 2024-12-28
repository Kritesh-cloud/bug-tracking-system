package cm.ex.bug.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "team__member__role")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeamRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private User member;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private DataHolder role;

    public TeamRole(User member, Team team, DataHolder role) {
        this.member = member;
        this.team = team;
        this.role = role;
    }
}
