package project.jobseekerplatform.Services.Implement;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.dto.NotificationDto;
import project.jobseekerplatform.Model.entities.Notification;
import project.jobseekerplatform.Model.entities.Post;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.NotificationRepo;
import project.jobseekerplatform.Persistences.PostRepo;
import project.jobseekerplatform.Persistences.UserRepo;
import project.jobseekerplatform.Services.NotificationService;

import java.util.ArrayList;
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
    public void sendPostNotification(NotificationDto notification) {
        Optional<User> sender = userRepo.findById(notification.getSenderId());
        Optional<Post> post = postRepo.findById(notification.getPostId());
        if (sender.isEmpty() || post.isEmpty()) {
            return;
        }

        List<User> followers = sender.get().getFollowers();

        for (User follower : followers) {
            simpMessagingTemplate.convertAndSendToUser(follower.getUsername(), "/notification", notification.getMessage());
        }

        Notification noti = new Notification();
        noti.setSender(sender.get());
        noti.setPost(post.get());
        noti.setMessage(notification.getMessage());
        noti.setReceivers(followers);
        notificationRepo.save(noti);
    }

    @Override
    public void sendLikeNotification(NotificationDto notification) {
        Optional<User> sender = userRepo.findById(notification.getSenderId()); //nguoi gui
        Optional<Post> post = postRepo.findById(notification.getPostId()); //bai dang
        if (sender.isEmpty() || post.isEmpty()) {
            return;
        }

        //gui cho nguoi dang bai viet do
        simpMessagingTemplate.convertAndSendToUser(post.get().getUser().getUsername(), "/notification", notification.getMessage());
        Notification noti = new Notification();
        noti.setSender(sender.get());
        noti.setPost(post.get());
        noti.setMessage(notification.getMessage());
        noti.setReceivers(List.of(post.get().getUser()));
        notificationRepo.save(noti);
    }

    @Override
    public List<NotificationDto> getAllNotificationsByUser(Integer userId) {
        List<NotificationDto> notificationDtos = new ArrayList<>();
        User user = userRepo.findById(userId).orElse(null);
        List<NotificationDto> likeNotification = notificationRepo.findAllByReceiversContaining(user).stream().map(
                (noti) -> {
                    NotificationDto dto = new NotificationDto();
                    dto.setSenderId(noti.getSender().getId());
                    dto.setPostId(noti.getPost().getId());
                    dto.setMessage(noti.getMessage());
                    dto.setSenderAvatar(noti.getSender().getProfilePicture());
                    return dto;
                }
        ).toList();
        return likeNotification;
    }
}
