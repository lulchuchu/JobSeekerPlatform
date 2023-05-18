package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.jobseekerplatform.Model.entities.Notification;
import project.jobseekerplatform.Model.entities.User;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {
    //    List<Notification> findAllByUserId(int userId);
    List<Notification> findAllByReceiversContaining(User userId);
}