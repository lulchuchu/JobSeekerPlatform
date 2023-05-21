package project.jobseekerplatform.Model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDto implements Serializable {
    private int id;
    private String message;
    private int senderId;
    private String senderName;
    private String senderAvatar;
    private int postId;
    private int receiverId;
}
