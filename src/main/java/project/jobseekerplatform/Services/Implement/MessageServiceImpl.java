package project.jobseekerplatform.Services.Implement;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import project.jobseekerplatform.Model.dto.MessageDto;
import project.jobseekerplatform.Model.dto.NotificationDto;
import project.jobseekerplatform.Model.dto.UserDtoBasic;
import project.jobseekerplatform.Model.entities.MessageE;
import project.jobseekerplatform.Model.entities.SenderReceiver;
import project.jobseekerplatform.Model.entities.User;
import project.jobseekerplatform.Persistences.MessageRepo;
import project.jobseekerplatform.Persistences.SenderReceiverRepo;
import project.jobseekerplatform.Persistences.UserRepo;
import project.jobseekerplatform.Services.MessageService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepo messageRepo;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepo userRepo;
    private final SenderReceiverRepo senderReceiverRepo;

    public MessageServiceImpl(MessageRepo messageRepo, SimpMessagingTemplate simpMessagingTemplate, UserRepo userRepo, SenderReceiverRepo senderReceiverRepo) {
        this.messageRepo = messageRepo;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userRepo = userRepo;
        this.senderReceiverRepo = senderReceiverRepo;
    }

    @Override
    public void sendMessage(MessageDto messageDto) {
        //Tao mot bien class Message moi va them thuoc tinh Dto
        MessageE message = new MessageE();
        User sender = userRepo.findById(messageDto.getSenderId()).get();
        User receiver = userRepo.findById(messageDto.getReceiverId()).get();

        if (senderReceiverRepo.findBySenderAndReceiver(sender, receiver).isEmpty()) {
            SenderReceiver senderReceiver = new SenderReceiver();
            senderReceiver.setSender(sender);
            senderReceiver.setReceiver(receiver);
            senderReceiverRepo.save(senderReceiver);
        }

        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContents(messageDto.getContents());
        message.setTime(LocalDateTime.now());

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setSenderId(sender.getId());
        notificationDto.setSenderName(sender.getName());
        notificationDto.setSenderAvatar(sender.getProfilePicture());
        notificationDto.setReceiverId(receiver.getId());
        notificationDto.setMessage("User " + sender.getName() + " has sent you a message " + messageDto.getContents());

        //Tao mot bien class MessageDto moi va them thuoc tinh tu class Message
        messageDto.setSenderName(message.getSender().getName());
        messageDto.setReceiverName(message.getReceiver().getName());
        messageDto.setSenderAvatar(message.getSender().getProfilePicture());
        messageDto.setReceiverAvatar(message.getReceiver().getProfilePicture());
        messageDto.setTime(message.getTime());
        //Gui tin nhan den nguoi nhan
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver().getName(), "/message", messageDto);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver().getName(), "/notification", notificationDto);
        messageRepo.save(message);
    }

    @Override
    public List<MessageDto> getChat(Integer senderId, Integer receiverId) {
        List<MessageE> messageEs1 = messageRepo.findAllBySenderIdAndReceiverId(senderId, receiverId);
        List<MessageE> messageEs2 = messageRepo.findAllBySenderIdAndReceiverId(receiverId, senderId);
        List<MessageE> messageEs = new ArrayList<>();
        messageEs.addAll(messageEs1);
        messageEs.addAll(messageEs2);
        List<MessageDto> result = new ArrayList<>(messageEs.stream().map((mess) -> {
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
        }).toList());
        result.sort(Comparator.comparing(MessageDto::getTime));
        return result;
    }

    @Override
    public List<UserDtoBasic> getChatList(Integer userId) {
//        HashSet<User> userDtoBasics = new HashSet<>();
//        for (MessageE messageE : messageRepo.findFirstGroupBySenderId(userId)) {
//            userDtoBasics.add(messageE.getReceiver());
//        }
//        for (MessageE messageE : messageRepo.findFirstGroupByReceiverId(userId)) {
//            userDtoBasics.add(messageE.getSender());
//        }
//        List<UserDtoBasic> userDtoBasicList = new ArrayList<>();
//        for (User user : userDtoBasics) {
//            UserDtoBasic userDtoBasic = new UserDtoBasic();
//            userDtoBasic.setId(user.getId());
//            userDtoBasic.setName(user.getName());
//            userDtoBasic.setProfilePicture(user.getProfilePicture());
//            userDtoBasicList.add(userDtoBasic);
//        }
//        return userDtoBasicList;
        HashSet<User> userDtoBasics = new HashSet<>();
        for (SenderReceiver senderReceiver : senderReceiverRepo.findAllBySender(userRepo.findById(userId).get())) {
            userDtoBasics.add(senderReceiver.getReceiver());
        }
        for (SenderReceiver senderReceiver : senderReceiverRepo.findAllByReceiver(userRepo.findById(userId).get())) {
            userDtoBasics.add(senderReceiver.getSender());
        }
        // Also add the current following user to userDtoBasics
        User userr = userRepo.findById(userId).get();
        userDtoBasics.addAll(userr.getFollowing());

        List<UserDtoBasic> userDtoBasicList = new ArrayList<>();
        for (User user : userDtoBasics) {
            UserDtoBasic userDtoBasic = new UserDtoBasic();
            userDtoBasic.setId((user).getId());
            userDtoBasic.setName((user).getName());
            userDtoBasic.setProfilePicture((user).getProfilePicture());
            userDtoBasicList.add(userDtoBasic);
        }
        return userDtoBasicList;
    }
}
