package project.jobseekerplatform.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException e) {
        ErrorMessage errorMessage = new ErrorMessage("Not found exception", e.getMessage(), 404, LocalDateTime.now());
        return ResponseEntity.status(404).body(errorMessage);
    }

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<ErrorMessage> handleResourceException(ResourceException e) {
        ErrorMessage errorMessage = new ErrorMessage("Resource exception", e.getMessage(), 400, LocalDateTime.now());
        return ResponseEntity.status(400).body(errorMessage);
    }

    @ExceptionHandler(AlreadyExistedException.class)
    public ResponseEntity<ErrorMessage> handleAlreadyExistException(AlreadyExistedException e) {
        ErrorMessage errorMessage = new ErrorMessage("Already exist exception", e.getMessage(), 400, LocalDateTime.now());
        return ResponseEntity.status(400).body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage("Exception", e.getMessage(), 500, LocalDateTime.now());
        return ResponseEntity.status(500).body(errorMessage);
    }
}