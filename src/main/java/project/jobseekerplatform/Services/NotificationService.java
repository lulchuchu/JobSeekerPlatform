package project.jobseekerplatform.Services;

import project.jobseekerplatform.Model.dto.NotificationDto;

public interface NotificationService {
    void sendNotification(NotificationDto notification);
}
