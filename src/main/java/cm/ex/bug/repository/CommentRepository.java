package cm.ex.bug.repository;

import cm.ex.bug.entity.Comment;
import cm.ex.bug.entity.Report;
import cm.ex.bug.entity.Team;
import cm.ex.bug.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {


    List<Comment> findByCommenter(User commenter);

    @Query(nativeQuery = true, value = "SELECT c FROM comment c WHERE c.report = :report ORDER BY c.createdAt ASC")
    List<Comment> findByReport(Report report);

//    List<Comment> findByTeam(Team team);

    List<Comment> findByCommenterAndReport(User commenter, Report report);

//    List<Comment> findByReportAndTeam(Report report, Team team);

}
