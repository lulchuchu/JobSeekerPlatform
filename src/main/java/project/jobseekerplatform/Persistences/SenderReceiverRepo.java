package project.jobseekerplatform.Persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.jobseekerplatform.Model.entities.SenderReceiver;
import project.jobseekerplatform.Model.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface SenderReceiverRepo extends JpaRepository<SenderReceiver, Integer> {
    List<SenderReceiver> findAllBySender(User sender);

    List<SenderReceiver> findAllByReceiver(User receiver);

    Optional<SenderReceiver> findBySenderAndReceiver(User sender, User receiver);
}
