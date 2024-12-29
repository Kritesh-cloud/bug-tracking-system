package cm.ex.bug.service;

import cm.ex.bug.entity.Notification;
import cm.ex.bug.entity.User;
import cm.ex.bug.repository.NotificationRepository;
import cm.ex.bug.repository.TeamRepository;
import cm.ex.bug.repository.UserRepository;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.service.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public BasicResponse createNotification(String notification) {


        return null;
    }

    @Override
    public BasicResponse createNotificationByTeam(String teamId, String notification) {
        return null;
    }

    @Override
    public List<Notification> listUserNotification() {
        return List.of();
    }

    private User getSystem(){
        Optional<User> system = userRepository.findByEmail("system@gmail.com");
        if(system.isEmpty()) throw new NoSuchElementException("System user not found");
        return system.get();
    }
}
