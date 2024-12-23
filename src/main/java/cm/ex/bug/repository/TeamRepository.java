package cm.ex.bug.repository;

import cm.ex.bug.entity.Team;
import cm.ex.bug.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {

    // Find teams by leader, ordered by updatedAt in ascending order
    List<Team> findByLeaderOrderByUpdatedAtAsc(User leader);

    // Find teams by teamMembers containing a specific user, ordered by updatedAt in ascending order
    @Query("SELECT t FROM Team t JOIN t.teamMembers m WHERE m = :user ORDER BY t.updatedAt ASC")
    List<Team> findByTeamMembersContainingOrderByUpdatedAtAsc(@Param("user") User user);

}
