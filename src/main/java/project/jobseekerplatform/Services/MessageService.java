package project.jobseekerplatform.Services;

import project.jobseekerplatform.Model.dto.MessageDto;
import project.jobseekerplatform.Model.dto.UserDtoBasic;

import java.util.List;

public interface MessageService {
    void sendMessage(MessageDto messageDto);

    List<MessageDto> getChat(Integer senderId, Integer receiverId);

    List<UserDtoBasic> getChatList(Integer userId);
}
