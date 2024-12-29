package cm.ex.bug.service.interfaces;

import cm.ex.bug.entity.Notification;
import cm.ex.bug.response.BasicResponse;

import java.util.List;

public interface NotificationService {

    public BasicResponse createNotification(String notification);

    public BasicResponse createNotificationByTeam(String teamId, String notification);

    public List<Notification> listUserNotification();

}
