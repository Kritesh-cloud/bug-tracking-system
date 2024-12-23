package cm.ex.bug.repository;

import cm.ex.bug.entity.Notification;
import cm.ex.bug.entity.Team;
import cm.ex.bug.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {


    List<Notification> findBySender(User sender);

    List<Notification> findByTeamSender(Team teamSender);
}
