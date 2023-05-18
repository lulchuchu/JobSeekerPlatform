package project.jobseekerplatform.Services.Implement;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.dto.MessageDto;
import project.jobseekerplatform.Model.entities.MessageE;
import project.jobseekerplatform.Persistences.MessageRepo;
import project.jobseekerplatform.Persistences.UserRepo;
import project.jobseekerplatform.Services.MessageService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepo messageRepo;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepo userRepo;

    public MessageServiceImpl(MessageRepo messageRepo, SimpMessagingTemplate simpMessagingTemplate, UserRepo userRepo) {
        this.messageRepo = messageRepo;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userRepo = userRepo;
    }

    @Override
    public void sendMessage(MessageDto messageDto) {
        MessageE message = new MessageE();
        message.setSender(userRepo.findById(messageDto.getSenderId()).get());
        message.setReceiver(userRepo.findById(messageDto.getReceiverId()).get());
        message.setContents(messageDto.getContents());
        message.setTime(LocalDateTime.now());
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver().getName(), "/message", messageDto);
        messageRepo.save(message);
    }

    @Override
    public List<MessageDto> getChat(Integer senderId, Integer receiverId) {
        List<MessageE> messageEs = messageRepo.findAllBySenderIdAndReceiverId(senderId, receiverId);
        return messageEs.stream().map((mess) -> {
            MessageDto messageDto = new MessageDto();
            messageDto.setSenderId(mess.getSender().getId());
            messageDto.setReceiverId(mess.getReceiver().getId());
            messageDto.setContents(mess.getContents());
            messageDto.setTime(mess.getTime());
            messageDto.setSenderName(mess.getSender().getName());
            messageDto.setReceiverName(mess.getReceiver().getName());
            messageDto.setSenderAvatar(mess.getSender().getProfilePicture());
            messageDto.setReceiverAvatar(mess.getReceiver().getProfilePicture());
            return messageDto;
        }).toList();
    }
}
