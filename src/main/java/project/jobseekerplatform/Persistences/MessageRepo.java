package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.jobseekerplatform.Model.entities.MessageE;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<MessageE, Integer> {
    List<MessageE> findAllBySenderIdAndReceiverId(Integer senderId, Integer receiverId);
}
