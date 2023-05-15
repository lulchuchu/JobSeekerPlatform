package project.jobseekerplatform.Services.Implement;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.dto.NotificationDto;
import project.jobseekerplatform.Model.entities.Notification;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.NotificationRepo;
import project.jobseekerplatform.Persistences.PostRepo;
import project.jobseekerplatform.Persistences.UserRepo;
import project.jobseekerplatform.Services.NotificationService;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final NotificationRepo notificationRepo;

    public NotificationServiceImpl(SimpMessagingTemplate simpMessagingTemplate, UserRepo userRepo, PostRepo postRepo, NotificationRepo notificationRepo) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.notificationRepo = notificationRepo;
    }

    @Override
    public void sendNotification(NotificationDto notification) {
        Notification noti = new Notification();
        Optional<User> user = userRepo.findById(notification.getUserId());
        if (user.isEmpty()) {
            return;
        }
        List<User> notificatedUser = user.get().getFollowers();
        noti.setUsers(notificatedUser);
        noti.setPost(postRepo.findById(notification.getPostId()).get());
        noti.setMessage(notification.getMessage());
        notificatedUser.stream().map(u ->
                {
                    simpMessagingTemplate.convertAndSendToUser(u.getUsername(), "/notification", notification.getMessage());
                    return null;
                }
        );
        notificationRepo.save(noti);

//        simpMessagingTemplate.convertAndSendToUser(notification.getUsername(), "/notification", notification.getMessage());

    }
}
