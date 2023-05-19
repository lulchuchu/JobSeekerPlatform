package project.jobseekerplatform.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.jobseekerplatform.Model.dto.MessageDto;
import project.jobseekerplatform.Security.UserDetail;
import project.jobseekerplatform.Services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@CrossOrigin
public class RestMessageController {
    private final MessageService messageService;

    public RestMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/chat")
    public ResponseEntity<?> getChat(Authentication auth, @RequestParam int receiverId) {
        UserDetail userDetail = (UserDetail) auth.getPrincipal();

        List<MessageDto> messageDtos = messageService.getChat(userDetail.getUser().getId(), receiverId);
        return ResponseEntity.ok(messageDtos);
    }
}
