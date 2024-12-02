package project.jobseekerplatform.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.jobseekerplatform.Security.UserDetail;
import project.jobseekerplatform.Services.MessageService;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatController {
    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getChatList(Authentication auth) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();
        return ResponseEntity.ok(messageService.getChatList(userDetail.getUser().getId()));
    }
}
