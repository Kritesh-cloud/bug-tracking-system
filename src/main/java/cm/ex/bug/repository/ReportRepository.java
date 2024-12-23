package cm.ex.bug.repository;

import cm.ex.bug.entity.DataHolder;
import cm.ex.bug.entity.Report;
import cm.ex.bug.entity.Team;
import cm.ex.bug.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {

    // Find reports by reporter and team
    List<Report> findByReporterAndTeam(User reporter, Team team);

    // Find reports by team
    List<Report> findByTeam(Team team);

    //Find reports by user
    List<Report> findByReporter(User reporter);

    // Find reports by assignees where only one User is sent as parameter
    List<Report> findByAssigneesContains(User assignee);

    // Find reports by assignees where only one User is sent as parameter and team
    List<Report> findByAssigneesContainsAndTeam(User assignee, Team team);

    // Find reports by status and team
    List<Report> findByStatusAndTeam(DataHolder status, Team team);

    // Find reports by resolution and team
    List<Report> findByResolutionAndTeam(DataHolder resolution, Team team);

    // Find reports by priority and team
    List<Report> findByPriorityAndTeam(DataHolder priority, Team team);

    // Find reports by severity and team
    List<Report> findBySeverityAndTeam(DataHolder severity, Team team);

    // Find reports by team and reportedDate from older date to current date
    List<Report> findByTeamOrderByReportedDateAsc(Team team);

    // Find reports by team and dueDate from earlier date to later date
    List<Report> findByTeamOrderByDueDateAsc(Team team);
}
