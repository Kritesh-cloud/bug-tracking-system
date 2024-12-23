package cm.ex.bug.service.interfaces;

import cm.ex.bug.entity.Notification;
import cm.ex.bug.response.BasicResponse;

import java.util.List;

public interface NotificationService {

    public BasicResponse createNotification();

    public BasicResponse createNotificationByTeam(String teamId);

    public List<Notification> listUserNotification();

}
