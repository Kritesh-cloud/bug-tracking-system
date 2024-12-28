package cm.ex.bug.repository;

import cm.ex.bug.entity.DataHolder;
import cm.ex.bug.entity.Team;
import cm.ex.bug.entity.TeamRole;
import cm.ex.bug.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeamRoleRepository extends JpaRepository<TeamRole, UUID> {

    Optional<TeamRole> findByMember(User member);

    List<TeamRole> findByTeam(Team team);

    Optional<TeamRole> findByRole(DataHolder role);
}
