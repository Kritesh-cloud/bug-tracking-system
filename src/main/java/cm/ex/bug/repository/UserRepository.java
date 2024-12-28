package cm.ex.bug.repository;


import cm.ex.bug.entity.Team;
import cm.ex.bug.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true,
            value = "SELECT u.id, u.created_at, u.email, u.name, u.password, u.profile_url, u.updated_at\n" +
                    "FROM users u\n" +
                    "JOIN user__authorities ua ON u.id = ua.user_id\n" +
                    "JOIN authority a ON ua.authority_id = a.id\n" +
                    "WHERE a.authority = 'admin'")
    List<User> listByUserAuthority(String authority);

    // Get all users from the leader or teamMembers attributes of the Team entity
    @Query("SELECT u FROM User u " +
            "WHERE u.id = :#{#team.leader.id} " +
            "OR u IN :#{#team.teamMembers}")
    List<User> listUsersByTeam(@Param("team") Team team);

    // Get all users from the leader or teamMembers attributes of the Team entity
    @Query("SELECT u FROM User u " +
            "WHERE u IN :#{#team.teamInvitations}")
    List<User> findUsersByTeamInvitations(@Param("team") Team team);

}
