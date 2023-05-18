package project.jobseekerplatform.Model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {
    private int id;
    private String contents;
    private int senderId;
    private String senderName;
    private String senderAvatar;
    private int receiverId;
    private String receiverName;
    private String receiverAvatar;
    private LocalDateTime time;
}
