package project.jobseekerplatform.Services;

import project.jobseekerplatform.Model.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    void sendPostNotification(NotificationDto notification);

    void sendLikeNotification(NotificationDto notification);

    List<NotificationDto> getAllNotificationsByUser(Integer userId);
}
