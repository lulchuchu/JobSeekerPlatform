package project.jobseekerplatform.Controller.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

//    public Notification receiveNotification(Notification notification) {
//        simpMessagingTemplate.convertAndSend("/notification/", notification.get);
//        return notification;
//    }
}
