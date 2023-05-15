package project.jobseekerplatform.Controller.websocket;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import project.jobseekerplatform.Model.dto.NotificationDto;
import project.jobseekerplatform.Services.NotificationService;

@Controller
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping("/receive-notification")
    public ResponseEntity<?> receiveNotification(@Payload NotificationDto notification) {
        notificationService.sendNotification(notification);
        return ResponseEntity.ok("Notification sent");
    }
}
