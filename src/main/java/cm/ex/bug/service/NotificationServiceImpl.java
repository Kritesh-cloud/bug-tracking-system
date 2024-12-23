package cm.ex.bug.service;

import cm.ex.bug.entity.Notification;
import cm.ex.bug.response.BasicResponse;
import cm.ex.bug.service.interfaces.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public BasicResponse createNotification() {
        return null;
    }

    @Override
    public BasicResponse createNotificationByTeam(String teamId) {
        return null;
    }

    @Override
    public List<Notification> listUserNotification() {
        return List.of();
    }
}
