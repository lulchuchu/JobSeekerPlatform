package project.jobseekerplatform.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Model.dto.NotificationDto;
import project.jobseekerplatform.Services.NotificationService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/notification")
public class RestNotiController {
    private final NotificationService notificationService;

    public RestNotiController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllNotifications(@RequestParam Integer userId) {
        List<NotificationDto> notifications = notificationService.getAllNotificationsByUser(userId);
        return ResponseEntity.ok(notifications);
    }
}
