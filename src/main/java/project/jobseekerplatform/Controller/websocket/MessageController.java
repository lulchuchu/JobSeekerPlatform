package project.jobseekerplatform.Controller.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.jobseekerplatform.Model.dto.MessageDto;
import project.jobseekerplatform.Services.MessageService;

@Controller
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate, MessageService messageService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
    }

    @MessageMapping("/receive-message")
    public void receiveMessage(@Payload MessageDto messageDto) {
        messageService.sendMessage(messageDto);
    }
}
